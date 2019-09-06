package com.koala.learn.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class WxEchartsForFFT {
    private TitleBean title;
    private TooltipBean tooltip;
    private LegendBean legend;
    private List<XAxisBean> xAxis;
    private List<YAxisBean> yAxis;

    private List<SeriesBean> series;

    public TitleBean getTitle() {
        return title;
    }

    public void setTitle(TitleBean title) {
        this.title = title;
    }

    public TooltipBean getTooltip() {
        return tooltip;
    }

    public void setTooltip(TooltipBean tooltip) {
        this.tooltip = tooltip;
    }

    public LegendBean getLegend() {
        return legend;
    }

    public void setLegend(LegendBean legend) {
        this.legend = legend;
    }

    public List<XAxisBean> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<XAxisBean> xAxis) {
        this.xAxis = xAxis;
    }

    public List<YAxisBean> getyAxis() {
        return yAxis;
    }

    public void setyAxis(List<YAxisBean> yAxis) {
        this.yAxis = yAxis;
    }

    public List<SeriesBean> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesBean> series) {
        this.series = series;
    }

    public static class TitleBean {
        public TitleBean(String text, String left) {
            this.text = text;
            this.left = left;
        }


        private String text;

        private String left;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }
    }
    public static class TooltipBean{
        private boolean show;
        private String trigger;

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

        public String getTrigger() {
            return trigger;
        }

        public void setTrigger(String trigger) {
            this.trigger = trigger;
        }

        public TooltipBean(boolean show, String trigger) {
            this.show = show;
            this.trigger = trigger;
        }
    }

    public static class LegendBean{
        private List<String> data;
        private String backgroundColor;

        public LegendBean(List<String> data, String backgroundColor) {
            this.data = data;
            this.backgroundColor = backgroundColor;
        }

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }
    }

    public static class XAxisBean {
        public XAxisBean(String name,String type, boolean scale, AxisLabelBean axisLabel) {
            this.name=name;
            this.type = type;
            this.scale = scale;
            this.axisLabel = axisLabel;
        }
        public XAxisBean(String type, boolean scale, AxisLabelBean axisLabel) {
            this.type = type;
            this.scale = scale;
            this.axisLabel = axisLabel;
        }

        /**
         * type : value
         * scale : true
         * axisLabel : {"formatter":"{value} cm"}
         */


        private String name;
        private String type;
        private boolean scale;
        private AxisLabelBean axisLabel;
        private List<String> data;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isScale() {
            return scale;
        }

        public void setScale(boolean scale) {
            this.scale = scale;
        }

        public AxisLabelBean getAxisLabel() {
            return axisLabel;
        }

        public void setAxisLabel(AxisLabelBean axisLabel) {
            this.axisLabel = axisLabel;
        }

        public static class AxisLabelBean {
            /**
             * formatter : {value} cm
             */

            private String formatter;
            private boolean show;
            private String textStyle;

            public AxisLabelBean() {
                JSONObject obj = new JSONObject();
                obj.put("color","#fff");
                textStyle = obj.toJSONString();
                show = true;
            }

            public String getFormatter() {
                return formatter;
            }

            public void setFormatter(String formatter) {
                this.formatter = formatter;
            }
        }
    }

    public static class YAxisBean{

        private String name;
        private String type;
        private boolean scale;
        private List<?> data;

        public YAxisBean(String name, String type, boolean scale, List<?> data) {
            this.name = name;
            this.type = type;
            this.scale = scale;
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isScale() {
            return scale;
        }

        public void setScale(boolean scale) {
            this.scale = scale;
        }

        public List<?> getData() {
            return data;
        }

        public void setData(List<?> data) {
            this.data = data;
        }
    }

    public static class SeriesBean{
        private String name;
        private String type;
        private boolean smooth;
        private List<?> data;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isSmooth() {
            return smooth;
        }

        public void setSmooth(boolean smooth) {
            this.smooth = smooth;
        }

        public List<?> getData() {
            return data;
        }

        public void setData(List<?> data) {
            this.data = data;
        }
    }
}
