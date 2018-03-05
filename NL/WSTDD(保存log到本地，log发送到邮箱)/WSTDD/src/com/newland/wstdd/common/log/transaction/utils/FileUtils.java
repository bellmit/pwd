package com.newland.wstdd.common.log.transaction.utils;

import java.io.File;

public final class FileUtils {

	/**
	 * Delete file(file or folder).
	 * 
	 * @param file
	 */
	public static void delete(File file) {
		if (file == null) {
			return;
		}
		if (file.isFile()) {
			file.delete();
			return;
		}

		File[] files = file.listFiles();
		if (files == null) {
			return;
		}
		for (File f : files) {
			if (f.isDirectory()) {
				delete(f);
			} else {
				f.delete();
			}
		}
		file.delete();
	}

}
