package com.ruoyi.system.config;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.ControlledRealTimeReopenThread;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author ilvyu.cn
 * @Date 2021/5/13 13:24
 * @Version 520.1314
 */
@Configuration
public class LuceneConfig {
    private static final String LUCENE_DIR= "/home/ruoyi/lucene";

    /**
     * 中文分词器
     * @return
     */
    @Bean
    public Analyzer analyzer(){
        return new SmartChineseAnalyzer();
    }

    /**
     * 索引位置
     * @return
     * @throws IOException
     */
    @Bean
    public Directory directory() throws IOException {
        Path path = Paths.get(LUCENE_DIR);
        File file = path.toFile();
        if(!file.exists()) {
            file.mkdirs();
        }
        return FSDirectory.open(path);
    }

    @Bean
    public IndexWriter indexWriter(Directory directory, Analyzer analyzer) throws IOException {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        indexWriter.deleteAll();
        indexWriter.commit();
        return indexWriter;
    }


//    @Bean
//    public SearcherManager searcherManager(Directory directory, IndexWriter indexWriter) throws IOException {
//        SearcherManager searcherManager = new SearcherManager(indexWriter, false, false, new SearcherFactory());
//        ControlledRealTimeReopenThread cRTReopenThead = new ControlledRealTimeReopenThread(indexWriter, searcherManager,
//                5.0, 0.025);
//        cRTReopenThead.setDaemon(true);
//        cRTReopenThead.setName("更新IndexReader线程");
//        cRTReopenThead.start();
//        return searcherManager;
//    }

}
