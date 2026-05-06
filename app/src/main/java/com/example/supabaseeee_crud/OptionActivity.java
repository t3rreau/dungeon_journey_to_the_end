package com.example.supabaseeee_crud;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.supabaseeee_crud.databinding.ActivityOptionBinding;

public class OptionActivity extends AppCompatActivity {

	private AppBarConfiguration appBarConfiguration;
	private ActivityOptionBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		binding = ActivityOptionBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		// --- Activation du Mode Immersif (Plein écran total) ---
		androidx.core.view.WindowInsetsControllerCompat windowInsetsController =
				androidx.core.view.WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());

		// On applique les réglages directement sans vérifier si c'est null
		windowInsetsController.hide(androidx.core.view.WindowInsetsCompat.Type.systemBars());
		windowInsetsController.setSystemBarsBehavior(
				androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
		);

		@SuppressLint({"MissingInflatedId", "LocalSuppress"}) RadioGroup radioGroupLanguage = findViewById(R.id.radioGroupLanguage);
		RadioButton radioEnglish = findViewById(R.id.radioEnglish);
		RadioButton radioFrench = findViewById(R.id.radioFrench);

		LocaleListCompat currentLocale = AppCompatDelegate.getApplicationLocales();
		if (currentLocale.toLanguageTags().contains("fr")) {
			radioFrench.setChecked(true);
		} else {
			radioEnglish.setChecked(true);
		}

		radioGroupLanguage.setOnCheckedChangeListener((group, checkedId) -> {
			if (checkedId == R.id.radioFrench) {
				AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("fr"));
			} else if (checkedId == R.id.radioEnglish) {
				AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"));
			}
		});
	}

	@Override
	public boolean onSupportNavigateUp() {
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_option);
		return NavigationUI.navigateUp(navController, appBarConfiguration)
				|| super.onSupportNavigateUp();
	}
}