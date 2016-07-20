package com.tom.stock.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/7/20.
 */
public class InitStockServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File file = new File(getClass().getResource("/file/stocks.txt").getFile());
        FileInputStream fis = new FileInputStream(file);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
        Calendar now = Calendar.getInstance();
        StockEndpoint api = new StockEndpoint();
        String line = in.readLine();
        while(line!=null){
            System.out.println(line);
            String[] tks = line.split(",");
            Stock stock = new Stock(tks[0], tks[1], 0, now.getTime());
            api.insert(stock);
            line = in.readLine();
        }
    }
}
