package com.wyverngame.anvil.publicizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

import com.google.common.io.ByteStreams;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;

public final class Publicizer {
	public static void main(String[] args) throws IOException {
		publicize(Paths.get("wurm/server/common.jar"), Paths.get("common-public.jar"));
		publicize(Paths.get("wurm/client/WurmLauncher/client.jar"), Paths.get("client-public.jar"));
		publicize(Paths.get("wurm/server/server.jar"), Paths.get("server-public.jar"));
	}

	private static void publicize(Path inPath, Path outPath) throws IOException {
		try (JarInputStream in = new JarInputStream(Files.newInputStream(inPath))) {
			try (JarOutputStream out = new JarOutputStream(Files.newOutputStream(outPath))) {
				JarEntry entry;
				while ((entry = in.getNextJarEntry()) != null) {
					if (entry.isDirectory()) {
						continue;
					}

					String name = entry.getName();
					out.putNextEntry(new JarEntry(name));

					if (name.endsWith(".class")) {
						ClassWriter writer = new ClassWriter(0);

						ClassReader reader = new ClassReader(in);
						reader.accept(new CheckClassAdapter(new ClassDefinalizer(new ClassPublicizer(writer)), true), 0);

						out.write(writer.toByteArray());
					} else {
						ByteStreams.copy(in, out);
					}
				}
			}
		}
	}
}
