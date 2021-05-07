package otta.user;

import org.junit.jupiter.api.Test;
import otta.data.reaction.Reaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class UserMatcherTest {

    @Test
    void shouldMapJobsFromList() {
        Reaction reaction1 = createReaction(123,456,false);
        Reaction reaction2 = createReaction(789, 1112, true);
        Reaction reaction3 = createReaction(123, 1112, false);
        List<Reaction> reactionList = asList(reaction1, reaction2, reaction3);

        UserMatcher userMatcher = new UserMatcher();
        Map<Integer, ArrayList<Integer>> jobsMap = userMatcher.mapJobs(reactionList);
        assertThat(jobsMap.get(456)).isNullOrEmpty();
        assertThat(jobsMap.get(1112).contains(789)).isEqualTo(true);
        assertThat(jobsMap.get(1112).contains(123)).isEqualTo(false);
    }

    @Test
    void shouldScoreUsers() {
        UserMatcher userMatcher = new UserMatcher();
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        map.put(1112, new ArrayList<>(asList(123,789,123,123,123)));
        map.put(456, new ArrayList<>(asList(321)));
        map.put(987, new ArrayList<>(asList(123,789)));

        userMatcher.scoreUsers(map);
        assertThat(userMatcher.getHighestScore()).isEqualTo(2);
        assertThat(userMatcher.getUser1()).isEqualTo(789);
        assertThat(userMatcher.getUser2()).isEqualTo(123);
    }

    private Reaction createReaction(int userId, int jobId, boolean direction) {
        Reaction reaction = new Reaction();
        reaction.setDirection(direction);
        reaction.setJobId(jobId);
        reaction.setUserId(userId);
        return reaction;
    }

}
