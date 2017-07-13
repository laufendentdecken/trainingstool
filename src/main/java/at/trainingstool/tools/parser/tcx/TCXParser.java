package at.trainingstool.tools.parser.tcx;

import static org.joox.JOOX.$;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;

import at.trainingstool.tools.parser.IParser;
import at.trainingstool.tools.parser.ParsingErrorException;
import at.trainingstool.tools.parser.model.Activity;
import at.trainingstool.tools.parser.model.Duration;
import at.trainingstool.tools.parser.model.Elevation;
import at.trainingstool.tools.parser.model.Lap;
import at.trainingstool.tools.parser.model.SportType;
import at.trainingstool.tools.parser.model.TrackPoint;

public class TCXParser implements IParser {

  @Override
  public List<Activity> parse(Path path) throws ParsingErrorException {

    List<Activity> activities = new ArrayList<>();
    List<Lap> laps = new ArrayList<>();
    try {
      $(path.toFile()).find("Activity").forEach(act -> {
        Activity activity = new Activity();
        activity.setSport(SportType.valueOf(act.getAttribute("Sport").toUpperCase()));
        activities.add(activity);
        $(act).find("Lap").filter(f -> !$(f).find("DistanceMeters").text().equals("0")).filter(f -> !$(f).find("TotalTimeSeconds").text().startsWith("0")).forEach(l -> {
          Lap lap = new Lap();
          lap.setDuration(new Duration(Double.parseDouble($(l).find("TotalTimeSeconds").text())));
          lap.setDistance(new BigDecimal(Double.parseDouble($(l).find("DistanceMeters").text())).setScale(0, RoundingMode.HALF_UP));
          lap.setDistance(lap.getDistance().divide(new BigDecimal(1000)).setScale(2, RoundingMode.HALF_UP));

          $(l).find("Trackpoint").forEach(tp -> {
            TrackPoint trackPoint = new TrackPoint();
            trackPoint.setLatitude(new BigDecimal($(tp).find("LatitudeDegrees").text()));
            trackPoint.setLongitude(new BigDecimal($(tp).find("LongitudeDegrees").text()));
            String altitudeMeters = $(tp).find("AltitudeMeters").text();
            if (altitudeMeters != null) {
              trackPoint.setAltitude(new BigDecimal(altitudeMeters).setScale(0, RoundingMode.HALF_UP));
            }
            lap.getPoints().add(trackPoint);
          });

          laps.add(lap);

        });

        for (int i = 0; i < laps.size(); i++) {
          Lap lap = laps.get(i);

          if (lap.getPoints().size() > 2 || (lap.getPoints().size() == 2 && lap.getDuration().longValue() <= 10) || i == (laps.size() - 1)) {
            lap.setElevation(getElevation(removeElevationMistakes(lap.getPoints())));

            activity.getLaps().add(lap);
            activity.setDuration(activity.getDuration().add(lap.getDuration()));
            activity.setDistance(activity.getDistance().add(lap.getDistance()));
            activity.getElevation().setPos(activity.getElevation().getPos().add(lap.getElevation().getPos()));
            activity.getElevation().setMin(activity.getElevation().getMin().add(lap.getElevation().getMin()));
          }
        }

        activity.setDuration(activity.getDuration().setScale(0, RoundingMode.HALF_UP));
        activity.getElevation().setPos(activity.getElevation().getPos().setScale(0, RoundingMode.HALF_UP));
        activity.getElevation().setMin(activity.getElevation().getMin().setScale(0, RoundingMode.HALF_UP));

      });
    } catch (SAXException | IOException e) {
      throw new ParsingErrorException();
    }

    return activities;
  }

  private List<TrackPoint> removeElevationMistakes(List<TrackPoint> points) {
    BigDecimal currentAltitude = null;
    int count = 0;

    List<TrackPoint> trackPoints = new ArrayList<>();

    for (TrackPoint point : points) {
      if (currentAltitude != null) {
        if (point.getAltitude().setScale(0, RoundingMode.HALF_UP).equals(currentAltitude)) {
          count++;
        } else {
          if (count >= 2) {
            trackPoints.add(point);
          }
          count = 0;
          currentAltitude = null;
        }
      } else {
        currentAltitude = point.getAltitude();
      }
    }

    return trackPoints;
  }

  private Elevation getElevation(List<TrackPoint> points) {
    BigDecimal lastAltitude = null;
    Elevation elevation = new Elevation();

    for (TrackPoint point : points) {
      if (point.getAltitude() != null) {
        if (lastAltitude != null) {
          BigDecimal elevationChange = point.getAltitude().setScale(0, RoundingMode.HALF_UP).subtract(lastAltitude.setScale(0, RoundingMode.HALF_UP));
          if (elevationChange.doubleValue() != 0) {
            if (elevationChange.doubleValue() > 0.0) {
              elevation.setPos(elevation.getPos().add(elevationChange));
            }
            if (elevationChange.doubleValue() < 0) {
              elevation.setMin(elevation.getMin().add(elevationChange));
            }
          }
        }
        lastAltitude = point.getAltitude();
      }
    }

    return elevation;
  }

  public static void main(String[] argv) throws Exception {
    List<File> files = new ArrayList<>();
    files.add(new File("/Users/KimmelF/Downloads/Move_2017_06_18_08_01_15_Laufen.tcx"));
    files.add(new File("/Users/KimmelF/Downloads/Move_2017_06_23_23_02_16_Trailrunning.tcx"));
    files.add(new File("/Users/KimmelF/Downloads/Move_2017_07_06_06_27_48_Laufen.tcx"));
    files.add(new File("/Users/KimmelF/Downloads/Move_2017_07_09_18_33_58_Laufen.tcx"));
    files.add(new File("/Users/KimmelF/Downloads/Move_2017_07_11_06_21_36_Laufen.tcx"));
    files.add(new File("/Users/KimmelF/Downloads/Move_2017_07_12_06_21_26_Radfahren.tcx"));

    for (File f : files) {
      for (Activity activity : new TCXParser().parse(f.toPath())) {
        System.out.println("Dauer(sec): " + activity.getDuration().doubleValue());
        System.out.println("Dauer: " + activity.getDuration().print());
        System.out.println("Länge(M): " + activity.getDistance());
        System.out.println("Höhe: " + activity.getElevation());
        System.out.println("Laps: " + activity.getLaps().size());
        // for (Lap lap : activity.getLaps()) {
        // System.out.println("-----LAP-----");
        // System.out.println("Dauer: " + lap.getDuration().print());
        // System.out.println("Dauer(sec): " + lap.getDuration());
        // System.out.println("Länge: " + lap.getDistance());
        // System.out.println("Höhe: " + lap.getElevation());
        // System.out.println("TP: " + lap.getPoints().size());
        // }

      }
    }

  }

}
