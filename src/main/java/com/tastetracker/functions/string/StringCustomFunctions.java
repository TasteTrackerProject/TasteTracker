package com.tastetracker.functions.string;

public class StringCustomFunctions
{

//    Zwraca String w taki sposób, że każda litera w słowie jest pisana dużą literą
    public static String formatTextFirstLetterBig( String input )
    {
        String[] words = input.split( " " );
        StringBuilder result = new StringBuilder();

        for ( String word : words )
        {
            if ( word.length() > 0 )
            {
                String firstLetter = word.substring(0, 1).toUpperCase();
                String restOfWord = word.substring( 1 ).toLowerCase();
                result.append( firstLetter ).append( restOfWord ).append(" ");
            }
        }


        if ( result.length() > 0 )
        {
            result.setLength( result.length() - 1 );
        }

        return result.toString();
    }


}
