# FriedGPT

FriedGPT is a fork of SpeakGPT

> [!NOTE]
> 
> This is a fork of a project which is a part of Dmytro Ostapenko's (the SpeakGPT dev's) Bachelor Thesis. Attribution is required to use his work. Copyright (c) 2023-2024 Dmytro Ostapenko. All rights reserved.
>
> Cite as: Dmytro Ostapenko (2024), "Review Program Automation Using Copilot Services" Bachelor Thesis, Technical University of Košice, 2024.

## Download

[Github Releases](https://github.com/ech0devv/fried-gpt/releases)


## API providers supported

- OpenAI (Full support)
- GROQ (Partial support)
- Azure (Partial support)
- OpenRouter (Text generation only, tested with Gemini, Claude, Perplexity, Llama, Gemma, Mistral, OpenAI models)
- Other (must be tested by community, don't be shy and provide your feedback)

> [!NOTE]
> 
> To change your API provider, go to settings and select the API endpoint. You can also add your custom API provider.


## Basic features
- [x] Chat (saved locally but can be imported/exported if needed)
- [x] Image generation via DALL-E
- [x] GPT 4 Vision
- [x] Voice input (Whisper and Google)
- [x] Voice Assistant (set as default in settings, and then used by swiping from bottom corners of screen)
- [x] FriedGPT in context and share menu
- [x] Function calling
- [x] ~~Prompts store~~ Removed by FriedGPT (because it's telemetry 🤢)
- [x] Material You
- [x] Various models (including GPT-4o)
- [x] AMOLED dark mode
- [x] Custom API provider support
- [x] Customize model parameters
- [x] Playground

## 🍗 Features added by FriedGPT
- [x] No telemetry, ads, or anything of that nature.
- [x] Better defaults
- [x] Automatic voice activation when you bring up the assistant.
- [x] Proper function execution (alarms, timers, duckduckgo, wikipedia, weather)
- [x] Other fixes and improvements I forgot to mention

## API key safety:

FriedGPT takes advantage of API keys for privacy and safety. All of the code is open source.
If you're concerned, please take the following precautions:

To secure your API key perform the following steps:

1. Make sure you have a separate API key for FriedGPT
2. Set up billing limit
3. Enable usage monitoring, so you can see what FriedGPT is doing and how much it costs

## License

```
Copyright (c) 2024 ech0devv. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

```
Copyright (c) 2023-2024 Dmytro Ostapenko. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
