/**************************************************************************
 * Copyright (c) 2023-2024 Dmytro Ostapenko. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **************************************************************************/

package dev.ech0.friedgpt.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.android.material.button.MaterialButton
import dev.ech0.friedgpt.ui.activities.MainActivity
import dev.ech0.friedgpt.R
import dev.ech0.friedgpt.preferences.ApiEndpointPreferences
import dev.ech0.friedgpt.preferences.Preferences
import dev.ech0.friedgpt.preferences.dto.ApiEndpointObject
import dev.ech0.friedgpt.util.Hash

class ActivationActivity : FragmentActivity() {

    private var btnNext: MaterialButton? = null
    private var keyInput: EditText? = null
    private var hostInput: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activation)

        btnNext = findViewById(R.id.btn_next)
        keyInput = findViewById(R.id.key_input)
        hostInput = findViewById(R.id.host_input)

        btnNext?.setOnClickListener {
            if (keyInput?.text.toString() == "") {
                Toast.makeText(this, "Please enter an API key", Toast.LENGTH_SHORT).show()
            } else if (hostInput?.text.toString() == "") {
                Toast.makeText(this, "Please enter API endpoint", Toast.LENGTH_SHORT).show()
            } else {
                val apiEndpointObject = ApiEndpointObject("Default", hostInput?.text.toString(), keyInput?.text.toString())
                val apiEndpointPreferences = ApiEndpointPreferences.getApiEndpointPreferences(this)
                apiEndpointPreferences.setApiEndpoint(this, apiEndpointObject)
                val gPreferences = Preferences.getPreferences(this, "")
                gPreferences.setApiEndpointId(Hash.hash("Default"))
                gPreferences.setApiKey(keyInput?.text.toString(), this)
                gPreferences.setCustomHost(hostInput?.text.toString())
                startActivity(Intent(this, MainActivity::class.java).setAction(Intent.ACTION_VIEW))
                finish()
            }
        }
    }
}
