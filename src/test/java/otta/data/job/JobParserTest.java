package otta.data.job;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class JobParserTest {

    @Test
    void shouldParseCsv() throws IOException {
        int companyId = 23;
        int jobId = 9975;

        String root = System.getProperty("user.dir");

        String jobsFileName="jobsTest.csv";
        String jobsFilePath = root+ File.separator+"src/test"+File.separator+"data"+File.separator+jobsFileName;
        JobParser jobParser = new JobParser();

        assertThat(jobParser.parse(jobsFilePath).get(0).getCompanyId()).isEqualTo(companyId);
        assertThat(jobParser.parse(jobsFilePath).get(0).getJobId()).isEqualTo(jobId);
    }

}
