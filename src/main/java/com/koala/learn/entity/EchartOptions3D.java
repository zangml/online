package com.koala.learn.entity;

import org.apache.commons.lang.StringUtils;

import java.util.List;

public class EchartOptions3D {
    private TooltipBean3D tooltip;
    private XAxisBean3D xAxis3D;
    private YAxisBean3D yAxis3D;
    private ZAxisBean3D zAxis3D;

    private SeriesBean3D series;

    private String background; //背景颜色设为白色

    private Grid3DBean grid3D;

    public TooltipBean3D getTooltip() {
        return tooltip;
    }

    public void setTooltip(TooltipBean3D tooltip) {
        this.tooltip = tooltip;
    }

    public XAxisBean3D getxAxis3D() {
        return xAxis3D;
    }

    public void setxAxis3D(XAxisBean3D xAxis3D) {
        this.xAxis3D = xAxis3D;
    }

    public YAxisBean3D getyAxis3D() {
        return yAxis3D;
    }

    public void setyAxis3D(YAxisBean3D yAxis3D) {
        this.yAxis3D = yAxis3D;
    }

    public ZAxisBean3D getzAxis3D() {
        return zAxis3D;
    }

    public void setzAxis3D(ZAxisBean3D zAxis3D) {
        this.zAxis3D = zAxis3D;
    }

    public SeriesBean3D getSeries() {
        return series;
    }

    public void setSeries(SeriesBean3D series) {
        this.series = series;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Grid3DBean getGrid3D() {
        return grid3D;
    }

    public void setGrid3D(Grid3DBean grid3D) {
        this.grid3D = grid3D;
    }

    public static class SeriesBean3D{
        private String type;
        private String[] dimensions; //显示框信息
        private List<double[]> data;//[12, 23, 43],[1,22,33]
        private int symbolSize;
        private ItemStyleBean itemStyle;
        public static class ItemStyleBean{
            private  int borderWidth;
            private String borderColor;
            private String color;
            public ItemStyleBean(){
                this.borderColor="rgba(255,255,255,0.8)";
                this.borderWidth=1;
                this.color="#f00";
            }
        }
        private EmphasisBean emphasis;
        public static class EmphasisBean{
            private ItemStyleBean itemStyle;
            public static class ItemStyleBean{
                private String color;
                public ItemStyleBean(){
                    this.color="#ccc";
                }
            }
            public EmphasisBean(){
                this.itemStyle=new ItemStyleBean();
            }

        }

        public SeriesBean3D(){

        }
        public SeriesBean3D(List<double[]> data){
            this.dimensions=new String[]{"x","y","z"};
            this.data=data;
            this.emphasis=new EmphasisBean();
            this.itemStyle=new ItemStyleBean();
            this.type="scatter3D";
            this.symbolSize=3;
        }
    }


    public static class Grid3DBean{
       private AxisLineBean axisLine;
       public static class AxisLineBean{
           private LineStyleBean lineStyle;
           public static class LineStyleBean{
               private String color;
               public LineStyleBean(){
                   this.color="#000";//轴线颜色
               }
           }
           public AxisLineBean(){
               this.lineStyle=new LineStyleBean();
           }
       }
       private AxisPointerBean axisPointer;
       public static class AxisPointerBean{
           private LineStyleBean lineStyle;
           private boolean show;
           public static class LineStyleBean{
               private String color;
               public LineStyleBean(){
                   this.color="#f00";//坐标轴指示线
               }
           }
           public AxisPointerBean(){
               this.lineStyle=new LineStyleBean();
               this.show=false;//不显示坐标轴指示线
           }
       }

       private ViewControlBean viewControl;
       public static class ViewControlBean{
           int beta;
           public ViewControlBean(){
               this.beta=10;
           }
       }
       private int boxWidth;
       private int boxHeight;
       private int boxDepth;
       private int top;

       public Grid3DBean(){
           this.axisLine=new AxisLineBean();
           this.axisPointer=new AxisPointerBean();
           this.boxDepth=100;
           this.boxHeight=100;
           this.boxWidth=200;
           this.top=-100;
           this.viewControl=new ViewControlBean();
       }
    }
    public static class TooltipBean3D {}//待补充

    public static class XAxisBean3D{
        private String type;
        private String name;

        public XAxisBean3D(String type,String name){
            this.type=type;
            this.name=name;
        }
        public XAxisBean3D(){
            this.name="x";
            this.type="value";
        }
    }
    public static class YAxisBean3D{
        private String type;
        private String name;

        public YAxisBean3D(String type,String name){
            this.type=type;
            this.name=name;
        }
        public YAxisBean3D(){
            this.name="x";
            this.type="value";
        }
    }
    public static class ZAxisBean3D{
        private String type;
        private String name;

        public ZAxisBean3D(String type,String name){
            this.type=type;
            this.name=name;
        }
        public ZAxisBean3D(){
            this.name="x";
            this.type="value";
        }
    }
}
