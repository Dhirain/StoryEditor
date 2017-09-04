package com.example.dj.storyeditor.ui.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.dj.storyeditor.R;
import com.example.dj.storyeditor.colorPallete.ColorChooserDialog;
import com.example.dj.storyeditor.colorPallete.ColorListener;
import com.example.dj.storyeditor.ui.custom.SaveAsDialog;
import com.example.dj.storyeditor.ui.presenter.MainScreenPresenter;
import com.example.dj.storyeditor.ui.view.Mainview;

import jp.wasabeef.richeditor.RichEditor;

public class MainActivity extends AppCompatActivity implements Mainview,SaveAsDialog.NoticeDialogListener {
    private static final String TAG = "mainActivity";
    private RichEditor mEditor;
    private String data;
    private MainScreenPresenter screenPresenter;
    private boolean loadDataFromIntent = false;
    private boolean isQuoteSelect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();
        setPresenter();
        handleIntent(getIntent());
        getPrefereceData();
        setEditorArtibutes();

    }

    private void setUI() {
        mEditor = (RichEditor) findViewById(R.id.editor);
        clickListner();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null && intent.getData() != null && intent.getData().getPath() != null) {

            data = screenPresenter.getDataFromFile(intent.getData().getPath(),intent.getData().getLastPathSegment());
            loadDataFromIntent = true;
            Log.d(TAG, "handleIntent: data from file" + data);
        }
    }


    //set presenter
    private void setPresenter() {
        screenPresenter = new MainScreenPresenter(this);
        screenPresenter.onViewAttached();
    }

    private void setEditorArtibutes(){
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.BLACK);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder("Insert text here...");
        mEditor.setHtml(data);

    }

    private void clickListner(){
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override public void onTextChange(String text) {
                data = text;
            }
        });

        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.undo();
            }
        });

        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.redo();
            }
        });

        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBold();
            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setItalic();
            }
        });

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setUnderline();
            }
        });


        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setIndent();
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setOutdent();
            }
        });

        findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });

        findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });

        findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });

        findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (!isQuoteSelect) {
                    mEditor.setBlockquote();
                    mEditor.setTextColor(Color.BLUE);
                    isQuoteSelect = true;
                }else {
                    mEditor.setOutdent();
                    mEditor.setTextColor(Color.BLACK);
                    isQuoteSelect = false;
                }

            }
        });

        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBullets();
            }
        });

        findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setNumbers();

            }
        });

        findViewById(R.id.action_strick).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setStrikeThrough();

            }
        });

        findViewById(R.id.action_color_pallete).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ColorChooserDialog dialog = new ColorChooserDialog(MainActivity.this);
                dialog.setTitle("color");
                dialog.setColorListener(new ColorListener() {
                    @Override
                    public void OnColorClick(View v, int color) {
                        Log.d(TAG, "OnColorClick: "+color);
                        mEditor.setTextColor(color);
                    }
                });
                dialog.show();
            }
        });

    }

    private void getPrefereceData() {
        if (!loadDataFromIntent) {
            data = screenPresenter.getPrevData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_screen_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_as:
                showSavePopUp();
                return true;
            case R.id.preview:
                goToPreviewScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showSavePopUp() {
        screenPresenter.checkPermision();
        SaveAsDialog saveAsDialog = SaveAsDialog.newInstance(this,screenPresenter.getCurrentFileName());
        saveAsDialog.show(getFragmentManager(),"save_as");
    }

    @Override
    public void goToPreviewScreen() {
       Intent intent = new Intent(this,PreviewActivity.class);
        intent.putExtra(PreviewActivity.PREVIEW_DATA,data);
        startActivity(intent);
        overridePendingTransition(R.anim.move_in_right, android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        screenPresenter.autoSave(data);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog,String fileName) {
        screenPresenter.saveFileAs(data,fileName);
    }


}