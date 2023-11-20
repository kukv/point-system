package jp.kukv.point.infrastructure.datasource

import kotlin.reflect.KClass

class Audit private constructor(val persistenceTime: PersistenceTime, val auditor: Auditor) {
    override fun toString(): String {
        return "Audit(persistenceTime=$persistenceTime, auditor=$auditor)"
    }

    companion object {
        fun create(kClass: KClass<*>): Audit {
            val className = kClass.qualifiedName ?: ""
            return Audit(PersistenceTime.now(), Auditor(className))
        }
    }
}
