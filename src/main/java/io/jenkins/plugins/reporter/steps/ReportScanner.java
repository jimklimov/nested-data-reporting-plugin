package io.jenkins.plugins.reporter.steps;

import hudson.FilePath;
import hudson.model.Run;
import hudson.model.TaskListener;

import java.io.IOException;

public class ReportScanner {

    private final Run<?, ?> run;

    private final FilePath workspace;
    
    private final Provider provider;

    private final TaskListener listener;

    public ReportScanner(final Run<?, ?> run, final Provider provider, final FilePath workspace, final TaskListener listener) {
        this.run = run;
        this.provider = provider;
        this.workspace = workspace;
        this.listener = listener;
    }
    
    public AnnotatedReport scan() throws IOException, InterruptedException {
        LogHandler logger = new LogHandler(listener, provider.getSymbolName());
        Report report = provider.scan(run, workspace, logger);
        AnnotatedReport annotatedReport = new AnnotatedReport(provider.getSymbolName(), report.getSubReports());
        logger.log(annotatedReport.getReport());

        return annotatedReport;
    }
}
