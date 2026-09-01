package com.app;

import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.PromtResultItemIF;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import de.codeshelf.consoleui.elements.ConfirmChoice;
import jline.TerminalFactory;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Programa interativo para fazer pedidos de pizza usando ConsoleUI
 * Demonstra uso de: Input, List, Checkbox, Choice e Confirmation prompts
 */
public class SimpleExample {

    public static void main(String[] args) throws InterruptedException {
        AnsiConsole.systemInstall();
        System.out.println(ansi().eraseScreen());
        System.out.println(ansi().render("@|red ╔════════════════════════════════════╗|@"));
        System.out.println(ansi().render("@|red ║ 🍕 BEM-VINDO À PIZZARIA ITALIA 🍕 ║|@"));
        System.out.println(ansi().render("@|red ╚════════════════════════════════════╝|@\n"));

        try {
            ConsolePrompt prompt = new ConsolePrompt();
            PromptBuilder promptBuilder = prompt.getPromptBuilder();

            // 1. Pedir nome do cliente
            promptBuilder.createInputPrompt()
                    .name("customerName")
                    .message("Qual é o seu nome?")
                    .defaultValue("Cliente")
                    .addPrompt();

            // 2. Selecionar tipo de pizza
            promptBuilder.createListPrompt()
                    .name("pizzaType")
                    .message("Qual pizza você deseja?")
                    .newItem().text("🍅 Margherita - Clássica com molho e queijo").add()
                    .newItem("veneziana").text("🌊 Veneziana - Com cebola e alho").add()
                    .newItem("hawai").text("🍍 Hawai - Com abacaxi e presunto").add()
                    .newItem("quattro").text("🌈 Quattro Stagioni - As 4 estações").add()
                    .newItem("carnivora").text("🥩 Carnívora - Para amantes de carnes").add()
                    .addPrompt();

            // 3. Selecionar adicionais (checkboxes)
            promptBuilder.createCheckboxPrompt()
                    .name("toppings")
                    .message("Selecione os adicionais desejados:")
                    .newSeparator("Proteínas").add()
                    .newItem("bacon").text("🥓 Bacon").add()
                    .newItem("presunto").text("🍗 Presunto").add()
                    .newItem("frango").text("🐔 Frango grelhado").add()
                    .newSeparator("Vegetais").add()
                    .newItem("cebola").text("🧅 Cebola").add()
                    .newItem("tomate").text("🍅 Tomate fresco").add()
                    .newItem("pimenta").text("🌶️ Pimenta vermelha").add()
                    .newSeparator("Queijos").add()
                    .newItem("catupiry").text("🧀 Catupiry").add()
                    .newItem("gorgonzola").text("🧀 Gorgonzola").add()
                    .addPrompt();

            // 4. Escolher tamanho (Choice prompt)
            promptBuilder.createChoicePrompt()
                    .name("size")
                    .message("Qual é o tamanho?")
                    .newItem("pequena").message("Pequena (6 fatias)").key('p').asDefault().add()
                    .newItem("media").message("Média (8 fatias)").key('m').add()
                    .newItem("grande").message("Grande (12 fatias)").key('g').add()
                    .addPrompt();

            // 5. Confirmar se é para entrega
            promptBuilder.createConfirmPromp()
                    .name("delivery")
                    .message("Deseja entrega em domicílio?")
                    .defaultValue(ConfirmChoice.ConfirmationValue.YES)
                    .addPrompt();

            // Executar todos os prompts
            HashMap<String, ? extends PromtResultItemIF> result = prompt.prompt(promptBuilder.build());

            // Exibir resumo do pedido
            exibirResumo(result);

        } catch (IOException e) {
            System.err.println("❌ Erro ao processar entrada: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                TerminalFactory.get().restore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Exibe um resumo formatado do pedido
     */
    private static void exibirResumo(HashMap<String, ? extends PromtResultItemIF> result) {
        System.out.println(ansi().render("@|green ╔════════════════════════════════════╗|@"));
        System.out.println(ansi().render("@|green ║        📋 RESUMO DO PEDIDO        ║|@"));
        System.out.println(ansi().render("@|green ╚════════════════════════════════════╝|@\n"));

        // Nome do cliente
        String customerName = result.get("customerName").toString();
        System.out.println(ansi().render("@|cyan Cliente:|@ " + customerName));

        // Pizza selecionada
        String pizza = result.get("pizzaType").toString();
        System.out.println(ansi().render("@|cyan Pizza:|@ " + pizza));

        // Adicionais
        @SuppressWarnings("unchecked")
        HashSet<String> toppings = (HashSet<String>) result.get("toppings");
        if (toppings != null && !toppings.isEmpty()) {
            System.out.print(ansi().render("@|cyan Adicionais:|@ "));
            System.out.println(String.join(", ", toppings));
        } else {
            System.out.println(ansi().render("@|cyan Adicionais:|@ Nenhum"));
        }

        // Tamanho
        String size = result.get("size").toString();
        System.out.println(ansi().render("@|cyan Tamanho:|@ " + size));

        // Entrega
        String deliveryResult = result.get("delivery").toString();
        String deliveryText = "YES".equalsIgnoreCase(deliveryResult) ? "Sim" : "Não (retirar na loja)";
        System.out.println(ansi().render("@|cyan Entrega:|@ " + deliveryText));

        System.out.println("\n" + ansi().render("@|green ✅ Pedido registrado com sucesso!|@"));
    }
}
