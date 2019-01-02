package com.shang.admin.bookstore;

import java.io.Serializable;
import java.util.List;

public class ISBNBookSubject implements Serializable {

    /**
     * rating : {"max":10,"numRaters":4094,"average":"9.4","min":0}
     * subtitle : 第 2 版·新版
     * author : ["（美）Brian W. Kernighan","（美）Dennis M. Ritchie"]
     * pubdate : 2004-1
     * tags : [{"count":2810,"name":"C","title":"C"},{"count":2176,"name":"c语言","title":"c语言"},{"count":2013,"name":"编程","title":"编程"},{"count":1373,"name":"计算机","title":"计算机"},{"count":1156,"name":"程序设计","title":"程序设计"},{"count":974,"name":"经典","title":"经典"},{"count":841,"name":"编程语言","title":"编程语言"},{"count":741,"name":"C/C++","title":"C/C++"}]
     * origin_title : The C Programming Language
     * image : https://img3.doubanio.com/view/subject/m/public/s1106934.jpg
     * binding : 平装
     * translator : ["徐宝文","李志译","尤晋元审校"]
     * catalog : 出版者的话
     专家指导委员会
     中文版序
     译者序
     校译者简介
     序
     第1版序
     引言
     第1章 导言
     1.1 入门
     1.2 变量与算术表态式
     1.3 for语句
     1.4 符号常量
     1.5 字符输入/输出
     1.6 数组
     1.7 函数
     1.8 参数——传值调用
     1.9 字符数组
     1.10 外部变量与作用域
     第2章 类型、运算符与表达式
     2.1 变量名
     2.2 数据类型及长度
     2.3 常量
     2.4 声明
     2.5 算术运算符
     2.6 关系运算符与逻辑运算符
     2.7 类型转换
     2.8 自增运算符与自减运算符
     2.9 按位运算符
     2.10 赋值运算符与表达式
     2.11 条件表达式
     2.12 运算符优先级与求值次序
     第3章 控制流
     3.1 语句与程序块
     3.2 if-else语句
     3.3 else-if语句
     3.4 switch语句
     3.5 whil循环与for特环
     3.6 do-while循环
     3.7 break语句与continue语句
     3.8 goto语句与标号
     第4章 涵数与程序结构
     第5章 指针与数组
     第6章 结构
     第7章 输入与输出
     第8章 UNIX系统接口
     附录A 参考手册
     附录B 标准库
     附录C 变更小结
     索引
     * pages : 258
     * images : {"small":"https://img3.doubanio.com/view/subject/s/public/s1106934.jpg","large":"https://img3.doubanio.com/view/subject/l/public/s1106934.jpg","medium":"https://img3.doubanio.com/view/subject/m/public/s1106934.jpg"}
     * alt : https://book.douban.com/subject/1139336/
     * id : 1139336
     * publisher : 机械工业出版社
     * isbn10 : 7111128060
     * isbn13 : 9787111128069
     * title : C程序设计语言
     * url : https://api.douban.com/v2/book/1139336
     * alt_title : The C Programming Language
     * author_intro : Brian W. Kernighan： 贝尔实验室计算科学研究中心高级研究人员，著名的计算机科学家。他参加了UNIX系统、C语言、AWK语言和许多其他系统的开发，同时出版了许多在计算机领域具有影响的著作，如《The Elements of Programming Style》、《The Practice of Programming》、《The UNIX Programming Environment》、《The AWK Language》、《Software Tools》等。
     Dennis M. Ritchie：1967年加入贝尔实验室。他和Ken L. Thompson两人共同设计并实现的C语言改变了程序设计语言发展的轨迹，是程序设计语言发展过程中的一个重要里程碑。与此同时，他们两人还设计并实现了UNIX操作系统。正是由于这两项巨大贡献，Dennis M. Ritchie于1983年获得了计算机界的最高奖——图灵奖。此外，他还获得了ACM、IEEE、贝尔实验室等授予的多种奖项.。
     * summary : 在计算机发展的历史上，没有哪一种程序设计语言像C语言这样应用广泛。本书原著即为C语言的设计者之一Dennis M.Ritchie和著名计算机科学家Brian W.Kernighan合著的一本介绍C语言的权威经典著作。我们现在见到的大量论述C语言程序设计的教材和专著均以此书为蓝本。原著第1版中介绍的C语言成为后来广泛使用的C语言版本——标准C的基础。人们熟知的“hello,World"程序就是由本书首次引入的，现在，这一程序已经成为众多程序设计语言入门的第一课。
     原著第2版根据1987年制定的ANSIC标准做了适当的修订．引入了最新的语言形式，并增加了新的示例，通过简洁的描述、典型的示例，作者全面、系统、准确地讲述了C语言的各个特性以及程序设计的基本方法。对于计算机从业人员来说，《C程序设计语言》是一本必读的程序设计语 言方面的参考书。
     * series : {"id":"1163","title":"计算机科学丛书"}
     * price : 30.00元
     */

    private RatingBean rating;
    private String subtitle;
    private String pubdate;
    private String origin_title;
    private String image;
    private String binding;
    private String catalog;
    private String pages;
    private ImagesBean images;
    private String alt;
    private String id;
    private String publisher;
    private String isbn10;
    private String isbn13;
    private String title;
    private String url;
    private String alt_title;
    private String author_intro;
    private String summary;
    private SeriesBean series;
    private String price;
    private List<String> author;
    private List<TagsBean> tags;
    private List<String> translator;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public SeriesBean getSeries() {
        return series;
    }

    public void setSeries(SeriesBean series) {
        this.series = series;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public List<String> getTranslator() {
        return translator;
    }

    public void setTranslator(List<String> translator) {
        this.translator = translator;
    }

    public static class RatingBean {
        /**
         * max : 10
         * numRaters : 4094
         * average : 9.4
         * min : 0
         */

        private int max;
        private int numRaters;
        private String average;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getNumRaters() {
            return numRaters;
        }

        public void setNumRaters(int numRaters) {
            this.numRaters = numRaters;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class ImagesBean {
        /**
         * small : https://img3.doubanio.com/view/subject/s/public/s1106934.jpg
         * large : https://img3.doubanio.com/view/subject/l/public/s1106934.jpg
         * medium : https://img3.doubanio.com/view/subject/m/public/s1106934.jpg
         */

        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class SeriesBean {
        /**
         * id : 1163
         * title : 计算机科学丛书
         */

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class TagsBean {
        /**
         * count : 2810
         * name : C
         * title : C
         */

        private int count;
        private String name;
        private String title;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
