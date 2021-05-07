package otta.data.reaction;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReactionParser {

 public ReactionParser(){}

 public List<Reaction> parse(String filePath) throws IOException {
  return new CsvToBeanBuilder(new FileReader(filePath))
          .withType(Reaction.class)
          .build()
          .parse();
 }
}
