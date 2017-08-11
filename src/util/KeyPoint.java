package util;


public class KeyPoint {
	String test = "C:/Users/Administrator/Downloads/7_rendered/15_keypoints.json";
	private double HEIGHT=1080, WIDTH=1920;
	private double xScale = 1,yScale = 1;
	private double x;
	private double y;
	private double p;
	public double getX() {
		return x*xScale;
	}
	public void setX(double x) {
		this.x = x/xScale;
	}
	public double getY() {
		return y*yScale;
	}
	public void setY(double y) {
		this.y = y/yScale;
	}
	
	public void setScale(double width,double height){
		xScale = width/WIDTH;
		yScale = height/HEIGHT;
	}
	
	public double getP() {
		return p;
	}
	public void setP(double p) {
		this.p = p;
	}
	public KeyPoint(double x, double y, double p) {
		this.x = x;
		this.y = y;
		this.p = p;
	}
	
	public KeyPoint(double x,double y){
		this.x = x;
		this.y = y;
		p = 0;
	}
	
	public KeyPoint toStdCS(){
		KeyPoint newpoint = new KeyPoint(x, HEIGHT-this.y, p);
		return newpoint;
	}
	
	public double slope(KeyPoint ap){
		return (ap.y-this.y)/(ap.x-this.x);
	}
	
	public double distance(KeyPoint ap){
		return Math.sqrt((Math.pow((ap.x-this.x),2) + (Math.pow((ap.y-this.y),2))));
	}
	
	public static double angle(KeyPoint a,KeyPoint b,KeyPoint c){
		double cos = ((a.x-b.x)*(c.x-b.x)+(a.y-b.y)*(c.y-b.y))/(a.distance(b)*c.distance(b));
		return Math.acos(cos)/Math.PI*180;
	}
	

	
	
	public void translate(int mode){
		if(mode == 0){
			return;
		}else if(mode == -1){
			setScale(HEIGHT*yScale,WIDTH*xScale);
			double tempx = y;
			double tempy = WIDTH - x;
			double temp = WIDTH;
			WIDTH = HEIGHT;
			HEIGHT = temp;
			y = tempy;
			x = tempx;
			
		}else if(mode == 1){
			setScale(HEIGHT*yScale,WIDTH*xScale);
			double tempy = x;
			double tempx = HEIGHT - y;
			double temp = WIDTH;
			WIDTH = HEIGHT;
			HEIGHT = temp;
			y = tempy;
			x = tempx;
			
		}
	}
}

//public static void main(String[] args) throws IOException, NoSuchCheckerException{
//Log.setLogPriority(5);
//int[] arr = {0,2,5,7,9,11,12,13,14,15,17,24,26,28,32};
//int[] arr2 = {6,8,10,18,21,27};
//int[] arr3 = {1,16,19,25,29,30,31,33,34};
//for(int i : arr3){
//	Map<String, KeyPoint> map = JsonParser.parseToMap("D:/json/"+i+"_keypoints.json")[0];
//	System.out.println(i+":"+KeyPoint.angle(map.get("rshoulder"), map.get("relbow"), map.get("rwrist")));
//	//System.out.println(i+":rknee-rankle:"+map.get("rhip").slope(map.get("neck")));
//	//System.out.println(i+":slope:"+(map.get("rshoulder").slope(map.get("rwrist"))));
//	
////	Checker checker = CheckerFactory.createChecker("beforepushup");
////	String[] strings = checker.checkit(map);
////	System.out.print(":");
////	for(String str:strings){
////		System.out.print(str+", ");
////	}
////	System.out.println("");
//}
//
//
//}