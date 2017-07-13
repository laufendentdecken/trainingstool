package at.trainingstool.tools.parser;

import java.nio.file.Path;
import java.util.List;

import at.trainingstool.tools.parser.model.Activity;

public interface IParser {

  public List<Activity> parse(Path path) throws ParsingErrorException;
}
