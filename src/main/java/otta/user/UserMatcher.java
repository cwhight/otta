package otta.user;

import otta.data.reaction.Reaction;

import java.util.*;

public class UserMatcher {

    private int highestScore = 0;
    private int user1 = 0;
    private int user2 = 0;

    public int getHighestScore() {
        return highestScore;
    }

    public int getUser1() {
        return user1;
    }

    public int getUser2() {
        return user2;
    }

    public Map<Integer, ArrayList<Integer>> mapJobs(List<Reaction> reactionList) {
        Map<Integer, ArrayList<Integer>> jobMap = new HashMap<>();
        for(Reaction reaction : reactionList) {
            boolean direction = reaction.getDirection();
            if(direction) {
                int jobId = reaction.getJobId();
                int userId = reaction.getUserId();

                if(!jobMap.containsKey(jobId)) {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(userId);
                    jobMap.put(jobId, list);
                } else {
                    ArrayList<Integer> value = jobMap.get(jobId);
                    value.add(userId);
                    jobMap.put(jobId, value);
                }
            }
        }
        return jobMap;
    }

    public void scoreUsers(Map<Integer, ArrayList<Integer>> jobMap) {
        Set<Integer> jobs = jobMap.keySet();
        Map<String, Integer> resultsMap = new HashMap<>();

        for(Integer job: jobs) {
            ArrayList<Integer> userListPerJob = new ArrayList<>(new HashSet<>(jobMap.get(job)));
            iterateThroughEachUserPerJob(resultsMap, userListPerJob);
        }
    }

    private void iterateThroughEachUserPerJob(Map<String, Integer> resultsMap, ArrayList<Integer> userListPerJob) {
        for(int i = 0; i< userListPerJob.size(); i++) {
            Integer currentUser = userListPerJob.get(i);
            iterateThroughNextUsers(resultsMap, userListPerJob, i, currentUser);
        }
    }

    private void iterateThroughNextUsers(Map<String, Integer> resultsMap, ArrayList<Integer> userListPerJob, int i, Integer currentUser) {
        for(int j = i +1; j< userListPerJob.size(); j++) {
            Integer nextUser = userListPerJob.get(j);
            if(nextUser != currentUser) {
                int smallest = (currentUser > nextUser) ? nextUser :  currentUser;
                int largest = (currentUser < nextUser) ? nextUser :  currentUser;
                String stringKey = smallest + " " + largest;
                updateMapWithScore(resultsMap, stringKey);
                updateHighestMatch(resultsMap, currentUser, nextUser, stringKey);
            }
        }
    }

    private void updateHighestMatch(Map<String, Integer> resultsMap, Integer currentUser, Integer nextUser, String stringKey) {
        if(resultsMap.get(stringKey) > highestScore) {
            highestScore = resultsMap.get(stringKey);
            user1 = currentUser;
            user2 = nextUser;
        }
    }

    private void updateMapWithScore(Map<String, Integer> resultsMap, String stringKey) {
        if (!resultsMap.containsKey(stringKey)) {
            resultsMap.put(stringKey, 1);
        } else {
            resultsMap.put(stringKey, resultsMap.get(stringKey) + 1);
        }
    }
}
