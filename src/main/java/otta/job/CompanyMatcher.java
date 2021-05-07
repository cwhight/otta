package otta.job;

import otta.data.job.Job;
import otta.data.reaction.Reaction;

import java.util.*;

public class CompanyMatcher {
    private int highestScore = 0;
    private int highestCompany1 =0;
    private int highestCompany2 =0;

    public Map<Integer, ArrayList<Integer>> mapUsers(List<Reaction> reactionList) {
        Map<Integer, ArrayList<Integer>> userMap = new HashMap<>();
        for(Reaction reaction : reactionList) {
            boolean direction = reaction.getDirection();
            if(direction) {
                int jobId = reaction.getJobId();
                int userId = reaction.getUserId();

                if(!userMap.containsKey(userId)) {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(jobId);
                    userMap.put(userId, list);
                } else {
                    ArrayList<Integer> value = userMap.get(userId);
                    value.add(jobId);
                    userMap.put(userId, value);
                }
            }
        }
        return userMap;
    }

    public Map<Integer, Integer> mapJobsToCompanies(List<Job> jobList) {
        Map<Integer, Integer> jobMap = new HashMap<>();
        for(Job job: jobList) {
            if(!jobMap.containsKey(job.getJobId())) {
                jobMap.put(job.getJobId(), job.getCompanyId());
            }
        }
        return jobMap;
    }

    public void scoreCompanies(Map<Integer, ArrayList<Integer>> userMap, Map<Integer, Integer> jobs) {
        Set<Integer> users = userMap.keySet();
        Map<String, Integer> resultsMap = new HashMap<>();

        for(int user: users) {
            ArrayList<String> matchedPairs = new ArrayList<>();
            List<Integer> jobsPerUser = userMap.get(user);
            iterateThroughJobs(jobs, resultsMap, matchedPairs, jobsPerUser);
        }

    }

    private void iterateThroughJobs(Map<Integer, Integer> jobs, Map<String, Integer> resultsMap, ArrayList<String> matchedPairs, List<Integer> jobsPerUser) {
        for(int i = 0; i< jobsPerUser.size(); i++) {
            int currentJob = jobsPerUser.get(i);
            iterateThroughRemainingMatchedJobs(jobs, resultsMap, matchedPairs, jobsPerUser, i, currentJob);
        }
    }

    private void iterateThroughRemainingMatchedJobs(Map<Integer, Integer> jobs, Map<String, Integer> resultsMap, ArrayList<String> matchedPairs, List<Integer> jobsPerUser, int i, int currentJob) {
        for(int j = i +1; j< jobsPerUser.size(); j++) {
            int nextJob = jobsPerUser.get(j);
            int thisCompany = jobs.get(currentJob);
            int nextCompany = jobs.get(nextJob);
            if(thisCompany != nextCompany) {
                int smallest = Math.min(thisCompany, nextCompany);
                int largest = Math.max(thisCompany, nextCompany);
                String uniquePair = smallest + " " + largest;
                addMatchIfNotAlreadyMatchedForThisUser(resultsMap, matchedPairs, thisCompany, nextCompany, uniquePair);
            }
        }
    }

    private void addMatchIfNotAlreadyMatchedForThisUser(Map<String, Integer> resultsMap, ArrayList<String> matchedPairs, int thisCompany, int nextCompany, String uniquePair) {
        if(!matchedPairs.contains(uniquePair)) {
            matchedPairs.add(uniquePair);
            updateMap(resultsMap, uniquePair);
            updateHighestScore(resultsMap, uniquePair, thisCompany, nextCompany);
        }
    }

    private void updateHighestScore(Map<String, Integer> resultsMap, String uniquePair, int thisCompany, int nextCompany) {
        if(resultsMap.get(uniquePair) > highestScore) {
            highestCompany1 = thisCompany;
            highestCompany2 = nextCompany;
            highestScore = resultsMap.get(uniquePair);
        }
    }

    private void updateMap(Map<String, Integer> resultsMap, String pair) {
        if(!resultsMap.containsKey(pair)) {
            resultsMap.put(pair, 1);
        } else {
            resultsMap.put(pair, resultsMap.get(pair) + 1);
        }
    }

    public int getHighestCompany1() {
        return highestCompany1;
    }

    public int getHighestCompany2() {
        return highestCompany2;
    }

    public int getHighestScore() {
        return highestScore;
    }
}
