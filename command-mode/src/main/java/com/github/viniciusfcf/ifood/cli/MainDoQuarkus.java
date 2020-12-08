package com.github.viniciusfcf.ifood.cli;

import java.util.Arrays;
import java.util.stream.Stream;

import javax.inject.Inject;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import picocli.CommandLine;

@TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true, subcommands = { Ola.class, Tchau.class })
public class MainDoQuarkus {

}

@CommandLine.Command(name = "ola", description = "Imprime o nome do usuario")
class Ola implements Runnable {

    @CommandLine.Option(names = { "-n", "--name"}, description = "Nome completo do usuario", defaultValue = "Fabricio Jacob")
    String nome;

    @Override
    public void run() {
        System.out.println("Ola run() " + nome);
    }
}

@CommandLine.Command(name = "tchau", description = "Imprime o nome do usuario")
class Tchau implements Runnable {

    @CommandLine.Option(names = {"-n", "--name"}, description = "Nome completo do usuario", defaultValue = "Fabricio Jacob")
    String nome;

    @Override
    public void run() {
        System.out.println("Tchau run() " + nome);
    }
}

//@QuarkusMain
/*public class MainDoQuarkus implements QuarkusApplication {

    @Inject
    @Channel("parametros")
    Emitter<String> emitter;
    //-Dquarkus.args="teste" para enviar argumentos via linha de comando
    @Override
    public int run(String... args) throws Exception {
        Stream.of(args).forEach(s -> emitter.send(s));
        System.out.println("MainDoQuarkus.run() " + Arrays.toString(args));
        Quarkus.waitForExit(); //para não matar a aplicacao
        return 0;
    }

}*/

//@QuarkusMain
//public class MainDoQuarkus {
//
//    public static void main(String[] args) {
//        Quarkus.run(MainDoQuarkus2.class, args);
//    }
//
//}
//
//class MainDoQuarkus2 implements QuarkusApplication {
//
//    @Override
//    public int run(String... args) throws Exception {
//        System.out.println("MainDoQuarkus.run(2) " + Arrays.toString(args));
//        Quarkus.waitForExit();
//        return 0;
//    }
//
//}
