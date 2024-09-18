/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.minorproject.e_commerce.model.service.impl


import com.google.firebase.Firebase
import com.minorproject.e_commerce.model.service.LogService
import javax.inject.Inject
import com.google.firebase.crashlytics.FirebaseCrashlytics

class LogServiceImpl @Inject constructor() : LogService {
  override fun logNonFatalCrash(throwable: Throwable) =
    FirebaseCrashlytics.getInstance().recordException(throwable)
}
