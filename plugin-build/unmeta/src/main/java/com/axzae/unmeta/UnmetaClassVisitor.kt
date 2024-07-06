package com.axzae.unmeta

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

class UnmetaClassVisitor(
    classVisitor: ClassVisitor,
) : ClassVisitor(Opcodes.ASM9, classVisitor), Opcodes {

    var isModified = false
        private set

    var modifiedClassName = ""
        private set

    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor? {
        return when (descriptor) {
            "Lkotlin/coroutines/jvm/internal/DebugMetadata;",
            "Lkotlin/jvm/internal/SourceDebugExtension;",
            "Lkotlin/Metadata;",
                -> {
                isModified = true
                modifiedClassName = descriptor.removeSuffix(";").split("/").last()
                null
            }
            else -> super.visitAnnotation(descriptor, visible)
        }
    }
}
