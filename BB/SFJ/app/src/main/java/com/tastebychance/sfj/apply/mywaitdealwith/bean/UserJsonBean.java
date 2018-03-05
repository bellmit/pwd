package com.tastebychance.sfj.apply.mywaitdealwith.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shenbinghong on 2018/2/9.
 */

public class UserJsonBean implements Serializable{

    /**
     * code : 0
     * data : [{"name":"局领导","value":2,"data":[{"value":18,"name":"局长","data":[{"value":26,"name":"局长","data":[{"value":20,"name":"张一"},{"value":44,"name":"产品"}]}]}]},{"name":"直属单位","value":3,"data":[{"value":29,"name":"市法律援助中心","data":[{"value":47,"name":"主任","data":[{"value":42,"name":"周二"},{"value":52,"name":"周五"}]}]}]},{"name":"司法所","value":4,"data":[{"value":27,"name":"玉屏司法所","data":[{"value":43,"name":"所长","data":[{"value":37,"name":"郑八"}]},{"value":44,"name":"司法助理员","data":[{"value":38,"name":"许九"}]}]}]}]
     */

    private String code;
    private List<DataBeanXXX> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBeanXXX> getData() {
        return data;
    }

    public void setData(List<DataBeanXXX> data) {
        this.data = data;
    }

    public static class DataBeanXXX implements Serializable{
        /**
         * name : 局领导
         * value : 2
         * data : [{"value":18,"name":"局长","data":[{"value":26,"name":"局长","data":[{"value":20,"name":"张一"},{"value":44,"name":"产品"}]}]}]
         */

        private String name;
        private int value;
        private List<DataBeanXX> data;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public List<DataBeanXX> getData() {
            return data;
        }

        public void setData(List<DataBeanXX> data) {
            this.data = data;
        }

        public static class DataBeanXX implements Serializable{
            /**
             * value : 18
             * name : 局长
             * data : [{"value":26,"name":"局长","data":[{"value":20,"name":"张一"},{"value":44,"name":"产品"}]}]
             */

            private int value;
            private String name;
            private List<DataBeanX> data;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<DataBeanX> getData() {
                return data;
            }

            public void setData(List<DataBeanX> data) {
                this.data = data;
            }

            public static class DataBeanX implements Serializable{
                /**
                 * value : 26
                 * name : 局长
                 * data : [{"value":20,"name":"张一"},{"value":44,"name":"产品"}]
                 */

                private int value;
                private String name;
                private List<DataBean> data;

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public List<DataBean> getData() {
                    return data;
                }

                public void setData(List<DataBean> data) {
                    this.data = data;
                }

                public static class DataBean implements Serializable {
                    /**
                     * value : 20
                     * name : 张一
                     */

                    private int value;
                    private String name;

                    public int getValue() {
                        return value;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }
            }
        }
    }
}
