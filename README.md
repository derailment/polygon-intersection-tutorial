<small>凸多邊形的重疊問題</small>
===
作業講解
<br>
<small>
    試教版
    <br>
    吳育姿
    <br>
    2018.05.13
</small>

---

### [前情提要] 長方形的重疊問題
* 某一次的作業...
* 給定長方形的四個座標，能否判斷兩個長方形是否重疊？
![](https://i.imgur.com/967NurK.jpg)

---

### [作法] 長方形的重疊問題
* 長方形必存在一點的座標(x, y)，使得
  * $x_{min} <= x<=x_{max}$
  * $y_{min}<=y<=y_{max}$
![](https://i.imgur.com/q2YVvEX.jpg)

---

### 多邊形的重疊問題
* 有同學問...
* 給定N凸邊形的N個座標，能否判斷兩個不規則凸邊形是否重疊？
![](https://i.imgur.com/egg5dtY.jpg)

---

### 作法
<small>
    <ul>
        <li> 任一條水平線穿過藍色多邊形
        <ul>
            <li>無交點</li>
            <li>兩個交點</li>
        </ul>
        </li>
        <li>
            若兩個交點的x座標<strong>同時</strong>大於（或小於）另一多邊形所有頂點, 則為<strong>重疊</strong>
        </li>
        <li>
            若另一多邊形<strong>存在一頂點</strong>的x座標<strong>介於</strong>兩個交點<strong>之間</strong>, 則為<strong>不重疊</strong>
        </li>
    </ul>
</small>

![](https://i.imgur.com/8VCD19d.jpg)

---

### [第一步驟] 畫圖
* 實線、虛線、點、文字
```java=
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
```

---

### [第二步驟] 畫多邊形
```java=
import java.util.*;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

public class Polygon {
	
	private int n;
	private double r;
	private double x0;
	private double y0;
	private double alpha;
	private Point2D[] points;
	private Line[] dashlines;
	private Line[] lines;
	
	Polygon(int n){
		this(n, 100, 200, 200, 0);
	}
	
	Polygon(int n, double r, double x0, double y0, double alpha){
		this.n = n;
		this.r = r;
		this.x0 = x0;
		this.y0 = y0;
		this.alpha = alpha;
		// Initialize points and dash lines
		points = new Point2D[n];
		dashlines = new Line[n];
		double theta = alpha;
		for(int i=0;i<n;i++){
			Point2D point = new Point2D(x0+r*Math.cos(theta), y0-r*Math.sin(theta));
			points[i] = point;
			Line line = new Line(x0,y0,point.getX(),point.getY());
			dashlines[i] = line;
			theta = theta + 2*Math.PI/n;
		}
		// Initialize solid lines
		lines = new Line[n];
		for(int i=0;i<n-1;i++){
			Line line = new Line(points[i].getX(),points[i].getY(),points[i+1].getX(),points[i+1].getY());
			lines[i] = line;
		}
		Line line = new Line(points[n-1].getX(),points[n-1].getY(),points[0].getX(),points[0].getY());
		lines[n-1] = line;	
	}
	
	public Point2D[] getPoints(){
		return points;
	}
	
	public Line[] getDashLines(){
		return dashlines;
	}
	
	public Line[] getLines(){
		return lines;
	}
	
	public double getCenterX(){
		return x0;
	}
	
	public double getCenterY(){
		return y0;
	}
	
	public int getSide(){
		return n;
	}

}

```

---

### [第三步驟] 判斷兩個交點是否在同側
![](https://i.imgur.com/gL9GBLa.jpg)

---

### [第三步驟] 判斷兩個交點是否在同側
![](https://i.imgur.com/KB3s5Sc.png)


---

### [第三步驟] 判斷兩個交點是否在同側
```java=

Point2D pt = new Point2D(250,250);
Line line1 = new Line(30,200,100,400);
Line line2 = new Line(270,200,340,400);
Line line3 = new Line(350,200,400,400);
Line line4 = new Line(420,200,470,400);

ArrayList<Line> twoline = new ArrayList<Line>();
twoline.add(line1);
twoline.add(line2);

if(isAnotherInsideOne(pt, twoline)){
    System.out.println((i)+" and "+(j)+" overlap.");
    break;
}
```

---

### [第三步驟] 判斷兩個交點是否在同側
```java=
boolean isAnotherInsideOne(Point2D pt, ArrayList<Line> twoline){
    double thresholdX = pt.getX();
    Line l0 = twoline.get(0);
    double maxy0 = Math.max(l0.getStartY(),l0.getEndY());
    double miny0 = Math.min(l0.getStartY(),l0.getEndY());
    double maxx0 = Math.max(l0.getStartX(),l0.getEndX());
    double minx0 = Math.min(l0.getStartX(),l0.getEndX());
    double x0;
    if((l0.getStartX()==minx0 && l0.getStartY()==miny0) || (l0.getStartX()==maxx0 && l0.getStartY()==maxy0)){
        x0 = maxx0 - (maxy0 - pt.getY()) / (maxy0 - miny0) * (maxx0 - minx0);
    }
    else{
        x0 = (maxy0 - pt.getY()) / (maxy0 - miny0) * (maxx0 - minx0) + minx0;
    }
    Line l1 = twoline.get(1);
    double maxy1 = Math.max(l1.getStartY(),l1.getEndY());
    double miny1 = Math.min(l1.getStartY(),l1.getEndY());
    double maxx1 = Math.max(l1.getStartX(),l1.getEndX());
    double minx1 = Math.min(l1.getStartX(),l1.getEndX());
    double x1;
    if((l1.getStartX()==minx1 && l1.getStartY()==miny1) || (l1.getStartX()==maxx1 && l1.getStartY()==maxy1)){
        x1 = maxx1 - (maxy1 - pt.getY()) / (maxy1 - miny1) * (maxx1 - minx1);
    }
    else{
        x1 = (maxy1 - pt.getY()) / (maxy1 - miny1) * (maxx1 - minx1) + minx1;
    }
    double maxX = Math.max(x0, x1);
    double minX = Math.min(x0, x1);
    if(thresholdX>=minX && thresholdX<=maxX)return true;
    return false;
}
```

---

### [作業] 判斷兩個多邊形是否重疊
請設計一個函數```isOverlap```，傳入兩個```Polygon```，若重疊返回```True```，反之返回```False```
```java=
bool isOverlap（Polygon p1, Polygon p2）
```
![](https://i.imgur.com/sETmK7U.png)
