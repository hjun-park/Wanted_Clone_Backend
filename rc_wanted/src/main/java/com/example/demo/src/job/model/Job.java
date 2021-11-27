package com.example.demo.src.job.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Job {
    public int jobIdx;
    public String title;
    public String image;

    public List<Job> subJobs;


    public Job(int jobIdx, String title, String image) {
        this.jobIdx = jobIdx;
        this.title = title;
        this.image = image;
    }
}
