package com.wyverngame.anvil.injector.trans.server;

import com.wyverngame.anvil.injector.InjectorException;
import com.wyverngame.anvil.injector.trans.FireEventInsnGenerator;
import com.wyverngame.anvil.injector.trans.MethodTransformer;
import com.wyverngame.anvil.injector.util.AsmUtils;
import com.wyverngame.anvil.injector.util.InsnMatcher;
import com.wyverngame.anvil.injector.util.QualifiedFieldNode;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class AddDugItemTransformer extends MethodTransformer {
    public AddDugItemTransformer() {
        super("com/wurmonline/server/behaviours/Terraforming", "dig", "(Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/items/Item;IIIFLcom/wurmonline/mesh/MeshIO;)Z");
    }

    @Override
    public void transform(ClassNode clazz, MethodNode method, InsnMatcher matcher) {
        LocalVariableNode performerVar = AsmUtils.getLocalVariable(method, "performer", "Lcom/wurmonline/server/creatures/Creature;");

        if (performerVar == null)
            throw new InjectorException("couldn't find performer variable");

        Iterator<AbstractInsnNode[]> it = matcher.match("INVOKEVIRTUAL", match -> {
            MethodInsnNode invoke = (MethodInsnNode) match[0];

            if (!invoke.owner.equals("com/wurmonline/server/creatures/Creature"))
                return false;

            if (!invoke.name.equals("getInventory"))
                return false;

            return invoke.desc.equals("()Lcom/wurmonline/server/items/Item;");
        });

        if (!it.hasNext())
            throw new InjectorException("failed to find injection point");

        AbstractInsnNode node = null;

        while (it.hasNext()) {
            AbstractInsnNode[] match = it.next();
            AbstractInsnNode n = match[0];
            AbstractInsnNode next = AsmUtils.getNextRealInsn(n);
            if (next instanceof VarInsnNode) {
                VarInsnNode nx = (VarInsnNode) next;
                if (nx.getOpcode() == Opcodes.ALOAD) {
                    node = n;
                    break;
                }
            }
        }

        if (node == null)
            throw new InjectorException("failed to find matching injection point");

        AbstractInsnNode previous = AsmUtils.getPreviousRealInsn(node);
        VarInsnNode loadNode = (VarInsnNode) AsmUtils.getNextRealInsn(node);
        AbstractInsnNode last = AsmUtils.getNextRealInsn(AsmUtils.getNextRealInsn(loadNode));

        LabelNode label = new LabelNode();

        InsnList before = new InsnList();
        before.add(FireEventInsnGenerator.generate("com/wyverngame/anvil/api/server/event/AddDugItemEvent", performerVar, AsmUtils.getLocalVariable(method, loadNode.var, loadNode)));
        before.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "com/wyverngame/anvil/api/event/EventContext", "isCancelled", "()Z", false));
        before.add(new JumpInsnNode(Opcodes.IFNE, label));
        method.instructions.insertBefore(previous, before);

        InsnList after = new InsnList();
        after.insert(label);
        method.instructions.insert(last, after);
    }
}
