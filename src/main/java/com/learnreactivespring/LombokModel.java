package com.learnreactivespring;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class LombokModel {

	private @NonNull String name;
	private @NonNull String age;
	private @NonNull String address;
}
