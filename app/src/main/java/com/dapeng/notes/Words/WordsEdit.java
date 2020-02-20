package com.dapeng.notes.Words;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.dapeng.notes.R;

public class WordsEdit extends AppCompatActivity {
    public String id,words,translation,phrase,others;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_edit);
        WordsInfo ThisWords = Words.lists.get(Words.position);
        //edit = Words.this.getLayoutInflater().inflate(R.layout.activity_words_edit,null);
        final EditText words_edit = findViewById(R.id.WordsInEdit);
        words_edit.setText(ThisWords.getWords());
        final EditText translation_edit = findViewById(R.id.TranslationInEdit);
        translation_edit.setText(ThisWords.getTranslation());
        final EditText phrase_edit = findViewById(R.id.PhraseInEdit);
        phrase_edit.setText(ThisWords.getPhrase());
        final EditText others_edit = findViewById(R.id.OthersInEdit);
        others_edit.setText(ThisWords.getOthers());
        findViewById(R.id.saveEditedWords).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                words = words_edit.getText().toString();
                translation = translation_edit.getText().toString();
                phrase = phrase_edit.getText().toString();
                others = others_edit.getText().toString();
                changeWordsToDB();
                Toast.makeText(WordsEdit.this,"修改成功",Toast.LENGTH_LONG).show();
            }
        });

    }
    public void changeWordsToDB(){
        ContentValues cv = new ContentValues();
        cv.put(WordsDB.WORDS,words);
        cv.put(WordsDB.TRANSLATION,translation);
        cv.put(WordsDB.PHRASE,phrase);
        cv.put(WordsDB.OTHERS,others);
        cv.put(WordsDB.TIME,Words.getTime());
        long rowID = Words.dbWriter.update(WordsDB.TABLE_NAME,cv,"_id=?",new String[]{Words.lists.get(Words.position).getId()});
        if(rowID!=-1){
            Toast.makeText(this,"单词"+words+"修改成功",Toast.LENGTH_LONG).show();
        }
    }
}
