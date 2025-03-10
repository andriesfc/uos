import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {
    idea
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
        languageLevel = IdeaLanguageLevel(JavaVersion.VERSION_21)
    }
}