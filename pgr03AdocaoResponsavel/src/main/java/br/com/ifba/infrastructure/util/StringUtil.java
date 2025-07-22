/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.infrastructure.util;

/**
 *
 * @author Jefferson S
 */
public class StringUtil {
    //Apenas verifica se uma string é nula ou vazia, serve para qualquer string dentro do projeto
    public static boolean isNullOrEmpty(String content){
        return content == null || content.trim().isEmpty();
    }
            
}
