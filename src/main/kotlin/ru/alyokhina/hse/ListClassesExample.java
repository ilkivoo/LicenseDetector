package ru.alyokhina.hse;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

import java.io.*;

public class ListClassesExample {

    public static void main(String[] args) throws FileNotFoundException {
        File initialFile = new File("/Users/alyokhina-o/LicenseDetector/src/main/kotlin/ru/alyokhina/hse/Hello.kt");
        Reader targetReader = new FileReader(initialFile);
        ParseResult<CompilationUnit> compilationUnit = new JavaParser().parse(targetReader);

        for (Comment comment : compilationUnit.getCommentsCollection().get().getComments()) {
            //System.out.println(comment.getContent());
            System.out.println(comment.getTokenRange().get().getBegin());
            System.out.println(comment.getTokenRange().get().getEnd());
        }
    }
}