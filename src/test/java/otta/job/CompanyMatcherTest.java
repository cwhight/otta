package otta.job;

import org.junit.jupiter.api.Test;
import otta.data.job.Job;
import otta.data.reaction.Reaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class CompanyMatcherTest {

    @Test
    void shouldMapUsersFromList() {
        Reaction reaction1 = createReaction(123,456,false);
        Reaction reaction2 = createReaction(789, 1112, true);
        Reaction reaction3 = createReaction(123, 1112, false);
        Reaction reaction4 = createReaction(100, 1112, true);
        Reaction reaction5 = createReaction(100, 800, false);
        List<Reaction> reactionList = asList(reaction1, reaction2, reaction3, reaction4, reaction5);

        CompanyMatcher matcher = new CompanyMatcher();
        Map<Integer, ArrayList<Integer>> users = matcher.mapUsers(reactionList);
        assertThat(users.get(123)).isNullOrEmpty();
        assertThat(users.get(789).contains(1112)).isEqualTo(true);
        assertThat(users.get(100).contains(1112)).isEqualTo(true);
        assertThat(users.get(100).contains(800)).isEqualTo(false);
    }

    @Test
    void shouldMapJobsToCompaniesFromList() {
        Job job1 = createJob(1,456);
        Job job2 = createJob(2, 1112);
        Job job3 = createJob(3, 1112);
        Job job4 = createJob(4, 1112);
        Job job5 = createJob(5, 800);
        List<Job> jobList = asList(job1, job2, job3, job4, job5);

        CompanyMatcher matcher = new CompanyMatcher();
        Map<Integer, Integer> jobsMap = matcher.mapJobsToCompanies(jobList);
        assertThat(jobsMap.get(1)).isEqualTo(456);
        assertThat(jobsMap.get(2)).isEqualTo(1112);
        assertThat(jobsMap.get(3)).isEqualTo(1112);
        assertThat(jobsMap.get(4)).isEqualTo(1112);
        assertThat(jobsMap.get(5)).isEqualTo(800);
    }

    @Test
    void shouldScoreCompanies() {
        CompanyMatcher matcher = new CompanyMatcher();
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1,546);
        map.put(2,1112);
        map.put(3,1112);
        map.put(4,1112);
        map.put(5,800);

        Map<Integer, ArrayList<Integer>> usersMap = new HashMap<>();

        usersMap.put(100,new ArrayList<>(asList(2,5)));
        usersMap.put(789,new ArrayList<>(asList(2)));
        usersMap.put(123,new ArrayList<>(asList(3,5)));

        matcher.scoreCompanies(usersMap, map);

        assertThat(matcher.getHighestScore()).isEqualTo(2);
        assertThat(matcher.getHighestCompany1()).isEqualTo(1112);
        assertThat(matcher.getHighestCompany2()).isEqualTo(800);
    }

    private Reaction createReaction(int userId, int jobId, boolean direction) {
        Reaction reaction = new Reaction();
        reaction.setDirection(direction);
        reaction.setJobId(jobId);
        reaction.setUserId(userId);
        return reaction;
    }

    private Job createJob(int jobId, int companyId) {
        Job job = new Job();
        job.setJobId(jobId);
        job.setCompanyId(companyId);
        return job;
    }
}
