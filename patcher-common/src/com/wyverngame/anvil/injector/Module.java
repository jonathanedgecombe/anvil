package com.wyverngame.anvil.injector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.ByteStreams;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.CheckClassAdapter;

public final class Module {
	private final ImmutableMap<String, ClassNode> classes;
	private final ImmutableMap<String, byte[]> files;
	private final Path output;

	public static Module read(Path path, Path api, Path output) throws IOException {
		ImmutableMap.Builder<String, ClassNode> classes = ImmutableMap.builder();
		ImmutableMap.Builder<String, byte[]> files = ImmutableMap.builder();

		try (JarInputStream in = new JarInputStream(Files.newInputStream(path))) {
			JarEntry entry;
			while ((entry = in.getNextJarEntry()) != null) {
				if (entry.isDirectory()) {
					continue;
				}

				String name = entry.getName();
				if (name.endsWith(".class")) {
					name = name.replaceAll(".class$", "");

					ClassNode clazz = new ClassNode();

					ClassReader reader = new ClassReader(in);
					reader.accept(clazz, 0);

					classes.put(name, clazz);
				} else {
					byte[] file = ByteStreams.toByteArray(in);
					files.put(name, file);
				}
			}
		}

		try (JarInputStream in = new JarInputStream(Files.newInputStream(api))) {
			JarEntry entry;
			while ((entry = in.getNextJarEntry()) != null) {
				if (entry.isDirectory()) {
					continue;
				}

				files.put(entry.getName(), ByteStreams.toByteArray(in));
			}
		}

		return new Module(classes.build(), files.build(), output);
	}

	private Module(ImmutableMap<String, ClassNode> classes, ImmutableMap<String, byte[]> files, Path output) {
		this.classes = classes;
		this.files = files;
		this.output = output;
	}

	public void write() throws IOException {
		try (JarOutputStream out = new JarOutputStream(Files.newOutputStream(output))) {
			for (Map.Entry<String, ClassNode> entry : classes.entrySet()) {
				String name = entry.getKey().concat(".class");
				ClassNode clazz = entry.getValue();

				ClassWriter writer = new ClassWriter(0);
				clazz.accept(new CheckClassAdapter(writer, true));

				out.putNextEntry(new JarEntry(name));
				out.write(writer.toByteArray());
			}

			for (Map.Entry<String, byte[]> entry : files.entrySet()) {
				String name = entry.getKey();
				byte[] file = entry.getValue();

				out.putNextEntry(new JarEntry(name));
				out.write(file);
			}
		}
	}

	public ClassNode getClass(String name) {
		return classes.get(name);
	}

	public ImmutableCollection<ClassNode> getClasses() {
		return classes.values();
	}

	public Path getOutput() {
		return output;
	}
}
