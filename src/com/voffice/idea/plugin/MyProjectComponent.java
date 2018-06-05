package com.voffice.idea.plugin;

import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.util.ProgressIndicatorUtils;
import com.intellij.openapi.progress.util.ReadTask;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiJavaFile;
import com.voffice.idea.plugin.directory.DirectoryManager;
import com.voffice.idea.plugin.generated.GeneratedEntityTable;
import com.voffice.idea.plugin.util.FindUseUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class MyProjectComponent implements ProjectComponent {

    private static Project project;

    public MyProjectComponent(Project p) {
        project = p;
    }

    @Override
    public void projectOpened() {
        System.out.println("项目打开");
        DumbService.getInstance(project).smartInvokeLater(()->{G.run(project);}, ModalityState.any());

    }

    @Override
    public void initComponent() {

    }

    public static Project getProject() {
        return project;
    }
}
