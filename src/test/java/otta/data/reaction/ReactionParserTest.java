package otta.data.reaction;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ReactionParserTest {

    @Test
    void shouldParseCsv() throws IOException {
        int userId = 123;
        int jobId = 456;
        boolean direction  = true;
        String root = System.getProperty("user.dir");
        String reactionsFileName="reactionsTest.csv";
        String reactionsFilePath = root+ File.separator+"src/test/data"+File.separator+reactionsFileName;        ReactionParser reactionParser = new ReactionParser();

        assertThat(reactionParser.parse(reactionsFilePath).get(0).getDirection()).isEqualTo(direction);
        assertThat(reactionParser.parse(reactionsFilePath).get(0).getUserId()).isEqualTo(userId);
        assertThat(reactionParser.parse(reactionsFilePath).get(0).getJobId()).isEqualTo(jobId);
    }
}
