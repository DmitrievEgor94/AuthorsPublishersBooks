//package com.mycompany.serializer.validators_of_objects;
//
//import com.mycompany.serializer.readers_of_objects.BracketsFinder;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//class PublishersValidatorInFile {
//
//    private static final String CLASS_OPEN_BRACKET = "{";
//    private static final String CLASS_CLOSE_BRACKET = "}";
//
//    private static final String DELIMITER_BETWEEN_FIELD_VALUE = ":";
//    private static final int NUMBER_OF_NEEDED_TOKENS = 2;
//    private static final int POSITION_OF_VALUE_TOKEN = 1;
//
//    static boolean validatePublishers(String publishersContent, String bookContent) {
//        List<Integer> openBracketPositions = BracketsFinder.getBracketPositions(booksContent, CLASS_OPEN_BRACKET);
//        List<Integer> closeBracketPositions = BracketsFinder.getBracketPositions(booksContent, CLASS_CLOSE_BRACKET);
//
//        List<Integer> authorsAvailableId = GetterAvailableIdList.getIdList(authorsContent);
//
//
//        static private List<Integer> getBooksAvailableId (String booksContent){
//            List<Integer> idList = new ArrayList<>();
//
//            List<Integer> openBracketPositions = BracketsFinder.getBracketPositions(booksContent, CLASS_OPEN_BRACKET);
//            List<Integer> closeBracketPositions = BracketsFinder.getBracketPositions(booksContent, CLASS_CLOSE_BRACKET);
//
//            for (int i = 0; i < openBracketPositions.size(); i++) {
//                int openBracketPosition = openBracketPositions.get(i);
//                int closeBracketPosition = closeBracketPositions.get(i);
//
//                String contentOfClass = authorsContent.substring(openBracketPosition + 1, closeBracketPosition);
//
//                Scanner scanner = new Scanner(contentOfClass);
//
//                scanner.next();
//                int id = scanner.nextInt();
//
//                idList.add(id);
//            }
//
//            return idList;
//        }
//    }
