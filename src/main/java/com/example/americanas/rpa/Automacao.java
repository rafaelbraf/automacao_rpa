package com.example.americanas.rpa;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.System.Logger;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.text.Document;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import com.example.americanas.rpa.model.Produto;
import com.google.common.collect.Table;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class Automacao {
	
	public static void main(String[] asgs) {
		
		org.slf4j.Logger logger =  LoggerFactory.getLogger(Automacao.class);		
		System.setProperty("webdriver.chrome.driver", "src\\drive\\chromedriver.exe");
		
		// Americanas		
		WebDriver navegador = new ChromeDriver();
		logger.info("Inicializando navegador");
		
		navegador.get("https://www.americanas.com.br");
		navegador.manage().window().maximize();
		navegador.findElement(By.xpath("//*[@id=\"rsyswpsdk\"]/div/header/div[1]/div[1]/div/div[1]/form/input")).click();
		navegador.findElement(By.xpath("//*[@id=\"rsyswpsdk\"]/div/header/div[1]/div[1]/div/div[1]/form/input")).sendKeys("3060", Keys.ENTER);
		logger.info("Pesquisando produto");
		
		new Select(new WebDriverWait(navegador, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"sort-by\"]")))).selectByValue("lowerPriceRelevance");
		logger.info("Ordenando por menor preço");
		
		List<Produto> listProdutos = new ArrayList<>();
		logger.info("Inicializando lista de Produtos");
		
		try {
			
			Thread.sleep(15000);
			logger.info("Esperando 15 segundos para percorrer a lista de Produtos");
			
			for (int i = 1; i <= 3; i++) {
				
				logger.info("Percorrendo produto " + i);
				logger.info("Pegando título e preço do Produto");
				String titleElement = navegador.findElement(By.xpath("//*[@id=\"rsyswpsdk\"]/div/main/div/div[3]/div[3]/div[" + i + "]/div/div/a/div[2]/div[2]/h3")).getText();
				String priceElement = navegador.findElement(By.xpath("//*[@id=\"rsyswpsdk\"]/div/main/div/div[3]/div[3]/div[" + i + "]/div/div/a/div[3]/span[1]")).getText();
				
				logger.info("Instanciando classe Produto passando título e preço");
				Produto produto = new Produto(titleElement, priceElement);
				
				logger.info("Adicionando Produto a lista de Produtos");
				listProdutos.add(produto);
				
			}
		} catch (InterruptedException ie) {
			logger.error("Erro ao percorrer por produtos e adicioná-los na lista" + ie.getMessage());
		}
		
		Comparator<Produto> comp = (Produto p1, Produto p2) -> {
			logger.info("Inicializando comparador dos Produtos");
			return p1.getPreco().compareTo(p2.getPreco());
		};
		
		logger.info("Ordenando Produtos pelo preço do menor para o maior");
		Collections.sort(listProdutos, comp);
		
		logger.info("Inicializando documento e formatador de datas");
		com.itextpdf.text.Document document = new com.itextpdf.text.Document();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
		
		try {
			
			logger.info("Pega a data e hora atual");
			Date currentDate = new Date();
			
			logger.info("Inicializando documento e definindo nome");
			PdfWriter.getInstance(document, new FileOutputStream("D://resultados_automacao/PDF_resultados_" + sdf.format(currentDate) + ".pdf"));
			
			logger.info("Abrir documento e adicionar parágrafo e linha separadora");
			document.open();
			document.add(new Paragraph("Teste Resultados"));
			document.add(new LineSeparator());
			
			logger.info("Inicializa tabela e adiciona células Produto e Preço");
			PdfPTable table = new PdfPTable(2);
			table.addCell("Produto");
			table.addCell("Preço");
			
			logger.info("Percorre lista de Produtos");
			for (var produto: listProdutos) {
				logger.info("Para cada produto percorrido pega o título e o preço e adiciona na tabela");
				table.addCell(produto.getTitulo());
				table.addCell(produto.getPreco());
			}
			
			logger.info("Adiciona a tabela no documento");
			document.add(table);
			
			logger.info("Adiciona linha separadora");
			document.add(new LineSeparator());
			
			document.add(new Paragraph("Fim"));			
			logger.info("Fim do preenchimento do documento");
			
		} catch (DocumentException de) {
			logger.error("Erro ao construir documento: " + de.getMessage());
		} catch (IOException ioe) {
			logger.error("Erro ao construir documento: " + ioe.getMessage());
		}
		
		document.close();
		logger.info("Fechamento do documento");
		
	}
	
}
