package otta.data.reaction;

import com.opencsv.bean.CsvBindByName;

public class Reaction {

    @CsvBindByName(column = "user_id")
    private int userId;

    @CsvBindByName(column = "job_id")
    private int jobId;

    @CsvBindByName(column = "direction")
    private boolean direction;

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }

    public int getJobId() {
        return this.jobId;
    }

    public boolean getDirection() {
        return this.direction;
    }
}
