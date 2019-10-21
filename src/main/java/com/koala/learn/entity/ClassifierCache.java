package com.koala.learn.entity;

public class ClassifierCache {
    private Integer id;

    private String file;

    private String options;

    private String classifier;

    private String accuracy;

    private String hash;

    public ClassifierCache(Integer id, String file, String options, String classifier, String accuracy, String hash) {
        this.id = id;
        this.file = file;
        this.options = options;
        this.classifier = classifier;
        this.accuracy = accuracy;
        this.hash = hash;
    }

    public ClassifierCache() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file == null ? null : file.trim();
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options == null ? null : options.trim();
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier == null ? null : classifier.trim();
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy == null ? null : accuracy.trim();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash == null ? null : hash.trim();
    }
}