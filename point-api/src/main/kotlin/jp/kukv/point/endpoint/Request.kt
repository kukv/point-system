package jp.kukv.point.endpoint

import am.ik.yavi.core.ConstraintViolations
import am.ik.yavi.core.Validator

interface Request<T> {
    val validator: Validator<T>

    fun validate(): ConstraintViolations
}
