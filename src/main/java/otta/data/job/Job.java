package otta.data.job;

import com.opencsv.bean.CsvBindByName;

public class Job {

    @CsvBindByName(column = "job_id")
    private int jobId;

    @CsvBindByName(column = "company_id")
    private int companyId;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
