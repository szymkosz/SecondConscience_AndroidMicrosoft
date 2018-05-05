/**
 * Class used for JSON formatting
 */
static class Documents {
    public List<Document> documents;

    public Documents() {
        this.documents = new ArrayList<Document>();
    }

    public void add(String id, String language, String text) {
        this.documents.add(new Document(id, language, text));
    }
}

/**
 * Class used for JSON formatting
 */
static class Document {
    public String id, language, text;

    public Document(String id, String language, String text) {
        this.id = id;
        this.language = language;
        this.text = text;
    }
}
