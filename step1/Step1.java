package step1;

/**
 *
 * @author abby
 */
import java.util.*;
import javafx.application.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.*;

public class Step1 extends Application {
	private Group group;
	@Override
	public void start(Stage primaryStage) {
		
                group = new Group();

                // add solid line
                Line line = new Line(50, 50, 100, 100);
                group.getChildren().add(line);
                
                // add dash line
                Line dashline = new Line(200, 200, 300, 300);
		dashline.getStrokeDashArray().addAll(2d);
		dashline.setStroke(Color.GRAY);
                group.getChildren().add(dashline);

                // add points
		Circle circle = new Circle();
		circle.setCenterX(150);
		circle.setCenterY(150);
		circle.setRadius(10);
                group.getChildren().add(circle);
                
                // add identifier
		Text identifier = new Text("Label");
		identifier.setStyle("-fx-font-size: 25;");
		identifier.setX(350);
		identifier.setY(350);
                group.getChildren().add(identifier);
                
		Scene scene = new Scene(group);
		primaryStage.setTitle("Step1");
		primaryStage.setWidth(500);
		primaryStage.setHeight(500);
		primaryStage.setScene(scene);
		primaryStage.show();
                
	}
	public static void main(String[] args){ 
		launch(args);
	}
}
