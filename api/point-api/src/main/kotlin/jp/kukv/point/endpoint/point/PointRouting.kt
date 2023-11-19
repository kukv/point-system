package jp.kukv.point.endpoint.point

import io.ktor.server.routing.Route
import jp.kukv.point.endpoint.point.PointCancelController.cancel
import jp.kukv.point.endpoint.point.PointController.findBy
import jp.kukv.point.endpoint.point.PointRegisterController.register
import jp.kukv.point.endpoint.point.PointUseController.use

fun Route.pointRouting() {
    findBy()
    register()
    use()
    cancel()
}
