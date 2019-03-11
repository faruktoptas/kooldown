# kooldown [![Status](https://travis-ci.org/faruktoptas/monitto.svg?branch=master)](https://travis-ci.org/faruktoptas/monitto) [ ![Download](https://api.bintray.com/packages/faruktoptas/kooldown/kooldown/images/download.svg) ](https://bintray.com/faruktoptas/kooldown/kooldown/_latestVersion)

Circular progress animation with duration

![!gif](https://user-images.githubusercontent.com/1595227/54090969-4c3b9880-438b-11e9-9697-e7ef9d81dbbe.gif)

## Download
```implementation 'me.toptas.kooldown:kooldown:0.1'```


## Usage
```xml
<me.toptas.kooldown.Kooldown android:layout_width="200dp"
		                     android:layout_centerInParent="true"
		                     android:id="@+id/kd"
		                     app:kd_activeColor="@color/colorAccent"
		                     app:kd_duration="2000"
		                     app:kd_inactiveColor="#dedede"
		                     app:kd_startAngle="90"
		                     app:kd_autoStart="true"
		                     app:kd_progress="270"
		                     app:kd_strokeWidth="20"
		                     android:layout_height="200dp"/>
```

## Parameters
 * `kd_duration`: Duration to complete animation
 * `kd_activeColor`: Active color of circle
 * `kd_inactiveColor`: Inactive color of circle
 * `kd_strokeWidth`: Stroke width of circle
 * `kd_startAngle`: Start point of animation (Does not effect duration or speed)
 * `kd_progress`: Max angle of animation. Default value is 360 for a complete circle
 * `kd_autoStart`: Automatically starts animation

## License
[Apache License 2.0](https://github.com/faruktoptas/kooldown/blob/master/LICENSE)