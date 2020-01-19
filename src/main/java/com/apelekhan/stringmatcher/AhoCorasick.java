package com.apelekhan.stringmatcher;

import java.util.*;

class TrieNode {
    public TrieNode(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public List<TrieNode> getChildren() {
        return children;
    }

    public TrieNode getChild(char character) {
        for (TrieNode node : children) {
            if (node.getCharacter() == character) {
                return node;
            }
        }

        return null;
    }

    public boolean containsChild(char character) {
        return getChild(character) != null;
    }

    public TrieNode getSuffixNode() {
        return suffixNode;
    }

    void setSuffixNode(TrieNode node) {
        suffixNode = node;
    }

    private char character;
    private String output;
    private List<TrieNode> children = new ArrayList<>();
    private TrieNode suffixNode = null;
}

public class AhoCorasick {
    public static class MatchResult {
        public MatchResult(String string, int position) {
            this.string = string;
            this.position = position;
        }

        public String getString() {
            return string;
        }

        public int getPosition() {
            return position;
        }

        private String string;
        private int position;
    }

    private static TrieNode buildTrie(String[] dictionary) {
        TrieNode rootNode = new TrieNode('$');

        for (String word : dictionary) {
            TrieNode currentNode = rootNode;

            for (int i = 0; i < word.length(); i++) {
                TrieNode nextNode = currentNode.getChild(word.charAt(i));

                if (nextNode == null) {
                    nextNode = new TrieNode(word.charAt(i));
                    currentNode.getChildren().add(nextNode);
                }

                if (i + 1 == word.length()) {
                    nextNode.setOutput(word);
                }

                currentNode = nextNode;
            }
        }

        Queue<TrieNode> queue = new LinkedList<>();

        for (TrieNode node : rootNode.getChildren()) {
            node.setSuffixNode(rootNode);
            queue.add(node);
        }

        while (!queue.isEmpty()) {
            TrieNode node = queue.remove();

            for (TrieNode child : node.getChildren()) {
                TrieNode parentNode = node.getSuffixNode();

                while (parentNode != null && !parentNode.containsChild(child.getCharacter())) {
                    parentNode = parentNode.getSuffixNode();
                }

                TrieNode suffixNode = parentNode != null ?
                        parentNode.getChild(child.getCharacter()) : rootNode;

                child.setSuffixNode(suffixNode);

                queue.add(child);
            }
        }

        return rootNode;
    }

    public static List<MatchResult> matchStrings(String[] dictionary, String text) {
        TrieNode rootNode = buildTrie(dictionary);

        List<MatchResult> results = new ArrayList<>();

        TrieNode currentNode = rootNode;

        for (int i = 0; i < text.length(); i++) {
            TrieNode nextNode = currentNode.getChild(text.charAt(i));

            if (nextNode == null) {
                TrieNode suffixNode = currentNode.getSuffixNode();

                while (suffixNode != null && !suffixNode.containsChild(text.charAt(i))) {
                    suffixNode = suffixNode.getSuffixNode();
                }

                if (suffixNode != null) {
                    nextNode = suffixNode.getChild(text.charAt(i));
                }
            }

            if (nextNode != null) {
                TrieNode node = nextNode;

                while (node != null) {
                    String output = node.getOutput();

                    if (output != null) {
                        results.add(new MatchResult(output, i - output.length() + 1));
                    }

                    node = node.getSuffixNode();
                }
            } else {
                nextNode = rootNode;
            }

            currentNode = nextNode;
        }

        results.sort((left, right) -> {
            if (left.getPosition() != right.getPosition()) {
                return left.getPosition() - right.getPosition();
            }

            return left.getString().compareTo(right.getString());
        });

        return results;
    }
}
