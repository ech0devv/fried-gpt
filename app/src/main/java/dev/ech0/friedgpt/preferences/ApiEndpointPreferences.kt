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

package dev.ech0.friedgpt.preferences

import android.content.Context
import android.content.SharedPreferences
import dev.ech0.friedgpt.preferences.dto.ApiEndpointObject
import dev.ech0.friedgpt.util.Hash

class ApiEndpointPreferences private constructor(private var preferences: SharedPreferences) {
    companion object {
        private var apiEndpointPreferences: dev.ech0.friedgpt.preferences.ApiEndpointPreferences? = null

        fun getApiEndpointPreferences(context: Context): dev.ech0.friedgpt.preferences.ApiEndpointPreferences {
            if (dev.ech0.friedgpt.preferences.ApiEndpointPreferences.Companion.apiEndpointPreferences == null) {
                dev.ech0.friedgpt.preferences.ApiEndpointPreferences.Companion.apiEndpointPreferences =
                    dev.ech0.friedgpt.preferences.ApiEndpointPreferences(
                        context.getSharedPreferences(
                            "api_endpoint",
                            Context.MODE_PRIVATE
                        )
                    )
            }

            return dev.ech0.friedgpt.preferences.ApiEndpointPreferences.Companion.apiEndpointPreferences!!
        }
    }

    private var listeners: ArrayList<dev.ech0.friedgpt.preferences.ApiEndpointPreferences.OnApiEndpointChangeListener> = ArrayList()

    fun addOnApiEndpointChangeListener(listener: dev.ech0.friedgpt.preferences.ApiEndpointPreferences.OnApiEndpointChangeListener) {
        listeners.add(listener)
    }

    fun getString(key: String, defValue: String): String {
        return preferences.getString(key, defValue)!!
    }

    fun putString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getApiEndpoint(context: Context, id: String): ApiEndpointObject {
        val label = getString(id + "_label", "")
        val host = getString(id + "_host", "")
        val apiKey: String =
            dev.ech0.friedgpt.preferences.EncryptedPreferences.Companion.getEncryptedPreference(
                context,
                "api_endpoint",
                id + "_api_key"
            )

        return ApiEndpointObject(label, host, apiKey)
    }

    fun deleteApiEndpoint(context: Context, id: String) {
        preferences.edit().remove(id + "_label").apply()
        preferences.edit().remove(id + "_host").apply()
        dev.ech0.friedgpt.preferences.EncryptedPreferences.Companion.setEncryptedPreference(
            context,
            "api_endpoint",
            id + "_api_key",
            "null"
        )

        for (listener in listeners) {
            listener.onApiEndpointChange()
        }
    }

    fun setApiEndpoint(context: Context, endpoint: ApiEndpointObject) {
        val id = Hash.hash(endpoint.label)
        putString(id + "_label", endpoint.label)
        putString(id + "_host", endpoint.host)
        dev.ech0.friedgpt.preferences.EncryptedPreferences.Companion.setEncryptedPreference(
            context,
            "api_endpoint",
            id + "_api_key",
            endpoint.apiKey
        )

        for (listener in listeners) {
            listener.onApiEndpointChange()
        }
    }

    fun editEndpoint(context: Context, label: String, endpoint: ApiEndpointObject) {
        deleteApiEndpoint(context, Hash.hash(label))
        setApiEndpoint(context, endpoint)
    }

    fun migrateFromLegacyEndpoint(context: Context) {
        if (getApiEndpointsList(context).isEmpty()) {
            val sp = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val label = "Default"
            val host = sp.getString("custom_host", "https://api.openai.com/v1/")
            val apiKey: String =
                dev.ech0.friedgpt.preferences.EncryptedPreferences.Companion.getEncryptedPreference(
                    context,
                    "api",
                    "api_key"
                )

            setApiEndpoint(context, ApiEndpointObject(label, host!!, apiKey))
        }
    }

    fun getApiEndpointsList(context: Context): ArrayList<ApiEndpointObject> {
        val list = ArrayList<ApiEndpointObject>()
        for (key in preferences.all.keys) {
            if (key.contains("_label")) {
                val id = key.replace("_label", "")
                val label = getString(id + "_label", "")
                val host = getString(id + "_host", "")
                val apiKey: String =
                    dev.ech0.friedgpt.preferences.EncryptedPreferences.Companion.getEncryptedPreference(
                        context,
                        "api_endpoint",
                        id + "_api_key"
                    )
                list.add(ApiEndpointObject(label, host, apiKey))
            }
        }

        // R8 bug fix
        if (list == null) {
            return ArrayList<ApiEndpointObject>()
        }

        return list
    }

    fun getApiEndpointByUrlOrNull(context: Context, url: String): ApiEndpointObject? {
        val list = getApiEndpointsList(context)
        for (endpoint in list) {
            if (endpoint.host == url) {
                return endpoint
            }
        }
        return null
    }

    fun findOpenAIKeyIfAvailable(context: Context): String? {
        val list = getApiEndpointsList(context)
        for (endpoint in list) {
            if (endpoint.host == "https://api.openai.com/v1/") {
                return endpoint.apiKey
            }
        }
        return null
    }

    fun interface OnApiEndpointChangeListener {
        fun onApiEndpointChange()
    }
}