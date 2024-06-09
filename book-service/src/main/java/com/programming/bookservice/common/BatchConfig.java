package com.programming.bookservice.common;

import com.programming.bookservice.domain.Book;
import com.programming.bookservice.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchConfig {

    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;
    private final BookRepository bookRepository;
    private final PlatformTransactionManager batchTransactionManager;
    private static final int BATCH_SIZE = 5;
    private final BookItemReader bookItemReader;
    public BatchConfig(JobLauncher jobLauncher, JobRepository jobRepository, BookRepository bookRepository, PlatformTransactionManager batchTransactionManager, BookItemReader bookItemReader) {
        this.jobLauncher = jobLauncher;
        this.jobRepository = jobRepository;
        this.bookRepository = bookRepository;
        this.batchTransactionManager = batchTransactionManager;
        this.bookItemReader = bookItemReader;
    }

    @Bean
    public Job updateBookPrice(){
        log.info("job");
        return new JobBuilder("first job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(chunkStep())
                .next(taskletStep())
                .build();
    }

    @Bean
    public Step taskletStep(){
        log.info("taskletStep");
        return new StepBuilder("taskled step",jobRepository)
                .tasklet(updateBookPricesTasklet(),batchTransactionManager)
                .build();
    }

    @Bean
    public Tasklet updateBookPricesTasklet(){
        return (StepContribution contribution, ChunkContext chunkContext) ->{
            List<Book> books = bookRepository.findAll();
            books.forEach(book -> {
                book.setPrice(book.getPrice().multiply(BigDecimal.valueOf(1.1)));
                bookRepository.save(book);
            });
            log.info("updateBookPricesTasklet");
            return RepeatStatus.FINISHED;
        };

    }

    @Bean
    public Step chunkStep(){
        log.info("chunkStep");
        return new StepBuilder("chunk",jobRepository)
                .<Book,Book>chunk(BATCH_SIZE,batchTransactionManager)
                .reader(bookItemReader)
                .writer(bookItemWriter())
                .build();
    }
//    private JobBuilderFactory jobBuilderFactory;
//
//    private StepBuilderFactory stepBuilderFactory;
//
//    private BookRepository bookRepository;
//
//    private BookItemReader bookItemReader;
//
//    @Bean
//    public Job updateBookPricesJob(){
//        return jobBuilderFactory.get("updateBookPricesJob")
//                .incrementer(new RunIdIncrementer())
//                .start(updateBookPricesStep())
//                .build();
//    }
//
//    @Bean
//    public Step updateBookPricesStep(){
//        return stepBuilderFactory.get("updateBookPricesStep")
//                .<Book, Book>chunk(10)
//                .reader(bookItemReader)
//                .processor(bookItemProcessor())
//                .writer(bookItemWriter())
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<Book,Book> bookItemProcessor(){
//        return book -> {
//            book.setPrice(book.getPrice().multiply(BigDecimal.valueOf(1.1)));
//            return book;
//        };
//    }
//
    @Bean
    public ItemWriter<Book> bookItemWriter(){
        log.info("writer");
        return books -> {
            books.forEach(book -> bookRepository.save(book));
            log.info("writer");
        };
    }

}
