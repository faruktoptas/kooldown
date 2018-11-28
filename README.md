# kooldown [![Status](https://travis-ci.org/faruktoptas/monitto.svg?branch=master)](https://travis-ci.org/faruktoptas/monitto) [![](https://jitpack.io/v/faruktoptas/kooldown.svg)](https://jitpack.io/#faruktoptas/kooldown)

Circular progress animation with duration

![!gif](https://user-images.githubusercontent.com/1595227/49186009-628d8d80-f374-11e8-8e71-2f4aff10080a.gif)

# Usage

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

Then, add the library to your module `build.gradle` under dependencies
```gradle
implementation 'com.github.faruktoptas:kooldown:1.0.0', {
    exclude group: 'com.android.support'
}
```



```xml
<me.toptas.kooldown.Kooldown android:layout_width="200dp"
		                     android:layout_centerInParent="true"
		                     android:id="@+id/kd"
		                     app:kd_activeColor="@color/colorAccent"
		                     app:kd_duration="2000"
		                     app:kd_inactiveColor="#dedede"
		                     app:kd_startAngle="90"
		                     app:kd_max_angle="360"
		                     app:kd_strokeWidth="12"
		                     android:layout_height="200dp"/>
```

# Parameters
 * `kd_duration`: Duration to complete animation
 * `kd_activeColor`: Active color of circle
 * `kd_inactiveColor`: Inactive color of circle
 * `kd_strokeWidth`: Stroke width of circle
 * `kd_startAngle`: Start point of animation (Does not effect duration or speed)
 * `kd_max_angle`: Max angle of animation. Default value is 360 for a complete circle

# License
[Apache License 2.0](https://github.com/faruktoptas/kooldown/blob/master/LICENSE)