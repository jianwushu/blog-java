package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.ruoyi.system.entity.Blog;
import com.ruoyi.system.entity.lucene.PageLucene;

import com.ruoyi.system.entity.vo.BlogLuceneVo;
import com.ruoyi.system.mapper.BlogMapper;
import com.ruoyi.system.service.LuceneService;

import com.ruoyi.system.util.LuceneUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;

import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ilvyu.cn
 * @Date 2021/5/13 13:41
 * @Version 520.1314
 */
@Service
public class LuceneServiceImpl implements LuceneService, ApplicationRunner {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private IndexWriter indexWriter;

    @Autowired
    private Analyzer analyzer;

    @Autowired
    private Directory directory;

    @Override
    public void createIndex() {
        QueryWrapper<Blog> qw = new QueryWrapper<>();
        List<Blog> blogs = blogMapper.selectList(qw);
        List<Document> docs = new ArrayList<Document>();
        for (Blog blog : blogs) {
            Document doc = new Document();
            doc.add(new StringField("id", blog.getId() + "", Field.Store.YES));
            doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
            doc.add(new TextField("text", blog.getText(), Field.Store.YES));
            docs.add(doc);
        }
        try {
            indexWriter.addDocuments(docs);
            indexWriter.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageLucene searchIndex(PageLucene pageLucene) {
        IndexReader reader = null;
        try {
            reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser parser = new QueryParser("text", LuceneUtil.getAnalyzer());
            Query query = parser.parse(pageLucene.getKey());

            //记录索引开始时间
            long startTime = System.currentTimeMillis();
            //开始查询，查询前 10 条数据，将记录保存在 docs 中
            TopDocs docs = searcher.search(query,pageLucene.getLimit());
            //记录索引结束时间
            long endTime = System.currentTimeMillis();
            System.out.println("匹配" + pageLucene.getKey() + "共耗时" + (endTime - startTime) + "毫秒");
            System.out.println("查询到" + docs.totalHits + "条记录");
            pageLucene.setTotal(docs.totalHits);

            Formatter formatter = new SimpleHTMLFormatter("<font color='red'>","</font>");
            QueryScorer scorer = new QueryScorer(query);
            Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
            Highlighter highlighter = new Highlighter(formatter,scorer);
            highlighter.setTextFragmenter(fragmenter);

            List<BlogLuceneVo> blist = new ArrayList<>();
            //取出每条查询结果
            for (ScoreDoc scoreDoc : docs.scoreDocs) {
                BlogLuceneVo blogLuceneVo = new BlogLuceneVo();
                //scoreDoc.doc相当于 docID，根据这个 docID 来获取文档
                Document doc = searcher.doc(scoreDoc.doc);
                String text = doc.get("text");
                blogLuceneVo.setId(Long.parseLong(doc.get("id")));
                blogLuceneVo.setTitle(doc.get("title"));
                if (text != null) {
                    TokenStream tokenStream = analyzer.tokenStream("text", new StringReader(text));
                    blogLuceneVo.setText(highlighter.getBestFragment(tokenStream, text));
                }
                blist.add(blogLuceneVo);
            }
            pageLucene.setBlogs(blist);

        } catch (IOException | ParseException | InvalidTokenOffsetsException e) {
            e.printStackTrace();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return pageLucene;
    }

    @Override
    public void updateIndex() {

    }

    @Override
    public void addIndex(Blog blog) {
        Document doc = new Document();
        doc.add(new StringField("id", blog.getId() + "", Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        doc.add(new TextField("text", blog.getText(), Field.Store.YES));
        try {
            indexWriter.addDocument(doc);
            indexWriter.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteIndex(Long[] ids) {

        try {
            for (Long id : ids) {
                indexWriter.deleteDocuments(new Term("id", String.valueOf(id)));
            }
            indexWriter.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createIndex();
    }
}
