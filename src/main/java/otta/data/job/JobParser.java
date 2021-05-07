package otta.data.job;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class JobParser {

 public JobParser(){}

 public List<Job> parse(String fileName) throws FileNotFoundException {
  return new CsvToBeanBuilder(new FileReader(fileName))
          .withType(Job.class)
          .build()
          .parse();
 }
}
