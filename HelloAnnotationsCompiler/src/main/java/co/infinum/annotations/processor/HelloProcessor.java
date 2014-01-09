package co.infinum.annotations.processor;

import co.infinum.annotations.Hello;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import javax.lang.model.SourceVersion;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;

/**
 * Created by ivan on 08/01/14.
 */
@SupportedAnnotationTypes("co.infinum.annotations.Hello")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class HelloProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "");

        StringBuilder builder = new StringBuilder();

        builder.append("package co.infinum.annotations.generated;\n");
        builder.append("\n");
        builder.append("public class HelloAnnotations {\n");
        builder.append("\n");
        builder.append("\tpublic String getMessage() {\n");
        builder.append("\t\treturn \"");


        for (Element element : roundEnv.getElementsAnnotatedWith(Hello.class)) {

            String objectType = element.getSimpleName().toString();

            Hello annotation = element.getAnnotation(Hello.class);

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, objectType + " says hello! ");

            builder.append(objectType + " says hello!\\n");

        }

        builder.append("\";\n");
        builder.append("\t}\n");
        builder.append("}\n");

        Filer filer = processingEnv.getFiler();

        try {
            JavaFileObject sourceFile = filer.createSourceFile("co.infinum.annotations.generated.HelloAnnotations");

            Writer writer = sourceFile.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {

        }

        return true;
    }
}
